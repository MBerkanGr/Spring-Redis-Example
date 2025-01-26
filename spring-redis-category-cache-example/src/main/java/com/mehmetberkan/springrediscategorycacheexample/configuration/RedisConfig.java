package com.mehmetberkan.springrediscategorycacheexample.configuration;

import com.mehmetberkan.springrediscategorycacheexample.model.ProductCategory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ProductCategory> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ProductCategory> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<ProductCategory> serializer = new Jackson2JsonRedisSerializer<>(ProductCategory.class);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        return template;
    }
}
