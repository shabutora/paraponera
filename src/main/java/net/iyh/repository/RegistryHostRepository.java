package net.iyh.repository;

import net.iyh.model.RegistryHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by tsukasa on 2015/09/09.
 */
@Repository
public class RegistryHostRepository {
  @Autowired
  @Qualifier("jsonRedisTemplate")
  RedisTemplate<String, Object> redisTemplate;

  public void save(RegistryHost host) {
    this.redisTemplate.opsForValue().set(host.getKey(), host);
  }
}
