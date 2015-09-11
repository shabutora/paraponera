package net.iyh.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.iyh.model.ContainerImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tsukasa.tamaru
 * @since 1.0.0
 */
@Repository
public class ContainerImageRepository {

  @Autowired
  StringRedisTemplate redisTemplate;

  @Autowired
  ObjectMapper mapper;

  public List<ContainerImage> getByHost(String hostName) {
    Set<String> keys = this.redisTemplate.keys(String.format("image:%s:*", hostName));
    return keys.stream().map(k -> this.getSingle(k)).collect(Collectors.toList());
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
