package com.hobbing.queue.infrastructre.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class SpringRedisTemplate extends RedisTemplate<String, String> {


}
