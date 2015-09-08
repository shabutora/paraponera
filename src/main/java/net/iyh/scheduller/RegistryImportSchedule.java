package net.iyh.scheduller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Docker Resitryのインポートスケジューラー
 * Created by tsukasa on 2015/09/07.
 */
@Component
public class RegistryImportSchedule {

  @Autowired
  StringRedisTemplate redisTemplate;

  @Scheduled(fixedRate = 10000)
  public void importImages() {
    redisTemplate.opsForValue().get("host");
  }
}
