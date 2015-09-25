package net.iyh.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.iyh.model.RegistryHost;
import net.iyh.model.response.Manifest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

/**
 * @author tsukasa.tamaru
 * @since 1.0.0
 */
@Repository
@Slf4j
public class ManifestRepository {

  @Autowired
  RegistryHostRepository hostRepository;

  @Autowired
  StringRedisTemplate stringRedisTemplate;

  @Autowired
  ObjectMapper mapper;

  @Value("${registry.v2.manifests}")
  String manifestUri;

  public Optional<Manifest> request(RegistryHost host, String image, String tag) {
    RestTemplate restTemplate = new RestTemplate();
    String uri = UriComponentsBuilder.fromUriString(host.getUrl())
                                         .pathSegment(image)
                                         .pathSegment(this.manifestUri)
                                         .pathSegment(tag).build().toUriString();
    try {
      ResponseEntity<Manifest> res = restTemplate.getForEntity(uri, Manifest.class);
      Manifest manifest = res.getBody();
      manifest.setHost(host.getName());
      manifest.setDigest(res.getHeaders().get("Docker-Content-Digest").get(0));
      return Optional.of(manifest);
    } catch (HttpClientErrorException ex) {
      log.warn("oh my god!!! holly shit!!!");
      ex.printStackTrace();
    }
    return Optional.empty();
  }

  public Optional<Manifest> get(String host, String image, String tag) {
    String key = Manifest.createKye(host, image, tag);
    String value = this.stringRedisTemplate.opsForValue().get(key);
    try {
      Manifest o = this.mapper.readValue(value, Manifest.class);
      return Optional.of(o);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return Optional.empty();
  }

  public Optional<Manifest> get(RegistryHost host, String image, String tag) {
    return this.get(host.getName(), image, tag);
  }

  public void save(Manifest manifest) {
    try {
      String value = this.mapper.writeValueAsString(manifest);
      this.stringRedisTemplate.opsForValue().set(manifest.getKey(), value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
