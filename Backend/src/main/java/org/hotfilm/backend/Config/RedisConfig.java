package org.hotfilm.backend.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port ;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    LettuceConnectionFactory redisConnectionFactory(){
       LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
       lettuceConnectionFactory.setHostName(host);
       lettuceConnectionFactory.setPort(port);
       lettuceConnectionFactory.setPassword(password);
    //   lettuceConnectionFactory.setPassword(password);
       return lettuceConnectionFactory;
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setStringSerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return redisTemplate;
    }
}
