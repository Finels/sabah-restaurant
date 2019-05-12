package com.fasteam.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Description: redis数据处理类
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public abstract class AbstractRedisService {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRedisService.class);

    public RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = this.getRedisTemplate().opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            LOG.error("redis写入缓存失败！", e);
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     */
    public boolean set(final String key, Object value, long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = this.getRedisTemplate().opsForValue();
            operations.set(key, value);
            this.getRedisTemplate().expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            LOG.error("redis写入缓存(设置时效时间)失败！", e);
        }
        return result;
    }

    /**
     * 批量删除对应的keys
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = this.getRedisTemplate().keys(pattern);
        if (keys.size() > 0) {
            this.getRedisTemplate().delete(keys);
        }
    }

    /**
     * 删除对应的key
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            this.getRedisTemplate().delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的key
     *
     * @param key
     */
    public boolean exists(final String key) {
        return this.getRedisTemplate().hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     */
    public Object get(final String key) {
        ValueOperations<Serializable, Object> operations = this.getRedisTemplate().opsForValue();
        return operations.get(key);
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = this.getRedisTemplate().opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = this.getRedisTemplate().opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = this.getRedisTemplate().opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param lBegin
     * @param lEnd
     */
    public List<Object> lRange(String k, long lBegin, long lEnd) {
        ListOperations<String, Object> list = this.getRedisTemplate().opsForList();
        return list.range(k, lBegin, lEnd);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key
     * @return
     */
    public Object rPop(String key) {
        return this.getRedisTemplate().opsForList().rightPop(key);
    }

    /**
     * 移除并获取列表第一个元素
     *
     * @param key
     * @return
     */
    public Object lPop(String key) {
        return this.getRedisTemplate().opsForList().leftPop(key);
    }

    /**
     * 返回列表大小
     *
     * @param key
     * @return
     */
    public Long lSize(String key) {
        return this.getRedisTemplate().opsForList().size(key);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public Long add(String key, Object value) {
        SetOperations<String, Object> set = this.getRedisTemplate().opsForSet();
        return set.add(key, value);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public Long sadd(String key, Object... value) {
        SetOperations<String, Object> set = this.getRedisTemplate().opsForSet();
        return set.add(key, value);
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     */
    public boolean sadd(String key, Object value, long expireTime) {
        boolean result = false;
        try {
            SetOperations<Serializable, Object> set = this.getRedisTemplate().opsForSet();
            set.add(key, value);
            this.getRedisTemplate().expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            LOG.error("redis写入缓存(设置时效时间)失败！", e);
        }
        return result;
    }

    public void expire(String key, long expireTime) {
        this.getRedisTemplate().expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 集合移除
     *
     * @param key
     * @param value
     */
    public Long sremove(String key, Object... value) {
        SetOperations<String, Object> set = this.getRedisTemplate().opsForSet();
        return set.remove(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = this.getRedisTemplate().opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = this.getRedisTemplate().opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoureBegin
     * @param scoureEnd
     */
    public Set<Object> rangeByScore(String key, double scoureBegin, double scoureEnd) {
        ZSetOperations<String, Object> zset = this.getRedisTemplate().opsForZSet();
        return zset.rangeByScore(key, scoureBegin, scoureEnd);
    }
}