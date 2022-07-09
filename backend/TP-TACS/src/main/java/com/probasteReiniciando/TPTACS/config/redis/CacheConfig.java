package com.probasteReiniciando.TPTACS.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;

@Configuration
public class CacheConfig {

    public final static String BLACKLIST_CACHE_NAME = "jwt";

    private final int tokenDuration = 18000;

    @Value("${spring.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.redis.port:6379}")
    private int redisPort;

    @Bean
    public RedisTemplate<Long, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Add some specific configuration here. Key serializers, etc.
        return template;
    }

}