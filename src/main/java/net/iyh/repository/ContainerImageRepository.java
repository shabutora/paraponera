package net.iyh.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.iyh.helper.RegistryHostHelper;
import net.iyh.model.ContainerImage;
import net.iyh.model.RegistryHost;
import net.iyh.model.response.Catalog;
import net.iyh.model.response.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tsukasa.tamaru
 * @since 1.0.0
 */
@Repository
@Slf4j
public class ContainerImageRepository {

  @Autowired
  StringRedisTemplate redisTemplate;

  @Autowired
  ObjectMapper mapper;

  @Autowired
  RestTemplate restTemplate;

  @Value("${registry.v2.catalog}")
  String catalogUri;
  @Value("${registry.v2.tags}")
  String tagsUri;

  /**
   * Registryホストに登録されているイメージカタログ
   * @param host Registryホスト
   * @return イメージカタログ
   */
  public List<String> requestCatalog(RegistryHost host) {
    String url = RegistryHostHelper.createUri(host, this.catalogUri);
    try {
      ResponseEntity<Catalog> res = this.restTemplate.getForEntity(url, Catalog.class);
      if (res.getStatusCode().is2xxSuccessful()) {
        return res.getBody().getRepositories();
      }
    } catch (HttpClientErrorException ex) {
      log.warn("Failed read catalog. " + url, ex);
    }
    return new ArrayList<>();
  }

  /**
   * コンテナイメージのリクエスト
   * @param host Registryホスト
   * @param name イメージ名
   * @return コンテナイメージ
   */
  public Optional<Image> requestImage(RegistryHost host, String name) {
    String uri = UriComponentsBuilder.fromUriString(host.getUrl())
        .pathSegment(name).pathSegment(this.tagsUri).build().toUriString();
    log.info(uri);
    try {
      ResponseEntity<Image> res = restTemplate.getForEntity(uri, Image.class);
      if (res.getStatusCode().is2xxSuccessful()) {
        return Optional.of(res.getBody());
      }
    } catch (HttpClientErrorException ex) {
      log.warn("OOOOOOOps!!! failed import tags. status = " + ex.getStatusCode().toString() + " " + uri);
    }
    return Optional.empty();
  }

  public List<ContainerImage> getByHost(String hostName) {
    Set<String> keys = this.redisTemplate.keys(String.format("image:%s:*", hostName));
    return keys.stream()
               .map(k -> this.getSingle(k))
               .sorted(Comparator.comparing(ContainerImage::getImageName))
               .collect(Collectors.toList());
  }

  public ContainerImage getSingle(String key) {
    String v = this.redisTemplate.opsForValue().get(key);
    try {
      return this.mapper.readValue(v, ContainerImage.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void save(ContainerImage image) {
    try {
      String v = this.mapper.writeValueAsString(image);
      this.redisTemplate.opsForValue().set(image.getKey(), v);
    } catch (JsonProcessingException e) {
      // TODO atode
      e.printStackTrace();
    }
  }

  public void delete(String hostName, String imageName) {
    this.redisTemplate.delete(ContainerImage.createKey(hostName, imageName));
  }
  public void delete(ContainerImage image) {
    this.redisTemplate.delete(image.getKey());
  }
}
