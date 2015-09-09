package net.iyh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by tsukasa on 2015/09/09.
 */
@Configuration
public class RedisConfiguration {

  /**
   * RedisTemplateにJSONシリアライザを設定
   * @param connectionFactory Redisコネクションファクトリ
   * @return RedisTemplate
   */
  @Bean
  public RedisTemplate<String, Object> jsonRedisTemplate(RedisConnectionFactory connectionFactory) {

    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    return template;
  }
}
