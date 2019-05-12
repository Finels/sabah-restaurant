package com.fasteam.common.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/10/20.
 */
@Service
public class RedisService extends AbstractRedisService {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }
}
