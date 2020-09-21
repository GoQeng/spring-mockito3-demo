package com.example.mockito.demo.config;

import org.mockito.Mockito;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DependencyConfig {

    @Bean
    public RedissonClient getRedisClient() {
        return Mockito.mock(RedissonClient.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return Mockito.mock(RestTemplate.class);
    }
}
