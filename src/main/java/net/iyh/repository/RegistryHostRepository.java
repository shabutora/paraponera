package net.iyh.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.iyh.model.RegistryHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by tsukasa on 2015/09/09.
 */
@Repository
public class RegistryHostRepository {

  @Autowired
  StringRedisTemplate stringRedisTemplate;

  @Autowired
  ObjectMapper mapper;

  public List<RegistryHost> get() {
    Set<String> keys = this.stringRedisTemplate.keys("registry:host:*");
    List<RegistryHost> list = keys.stream().map(k -> this.get(k)).collect(Collectors.toList());
    return list;
  }

  /**
   * Registry Hostの取得
   * @param key キー文字列
   */
  public RegistryHost get(String key) {
    String s = this.stringRedisTemplate.opsForValue().get(key);
    try {
      return mapper.readValue(s, RegistryHost.class);
    } catch (IOException e) {
      // TODO atode
      throw new RuntimeException(e);
    }
  }

  /**
   * Registryホストの保存
   * @param host Registryホスト情報
   */
  public void save(RegistryHost host) {
    try {
      String s = this.mapper.writeValueAsString(host);
      this.stringRedisTemplate.opsForValue().set(host.getKey(), s);
    } catch (JsonProcessingException e) {
      // TODO atode
      throw new RuntimeException(e);
    }
  }

  /**
   * 指定ホストの削除
   * @param name ホスト名
   */
  public void delete(String name) {
    String key = RegistryHost.createKey(name);
    this.stringRedisTemplate.delete(key);
  }
}
