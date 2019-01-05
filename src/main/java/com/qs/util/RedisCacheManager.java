package com.qs.util;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RedisCacheManager implements CacheManager {

    private RedisManager redisManager;

    private static ConcurrentMap<String,Cache> caches = new ConcurrentHashMap<>();

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    //根据指定的名字获取Cache(为什么需要根据名字？因为CacheManager就相当于一个大容器，只管理Cache<K,V>，
    //而这个Cache<K,V>是个小容器，根据键可以取到对应的值。在shiro里面，AuthenticationInfo（用户登陆信息），
    //AuthorizationInfo(授权信息)生成一个Cache，然后根据唯一的名字存入到CacheManager里)，
    //如果不存在，则创建个Cache存入到CacheManager里然后返回。
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {

        Cache cache = caches.get(name);

        if(cache == null){
            redisManager.init();
            cache = new RedisCache(redisManager);
        }

        caches.put(name,cache);

        return cache;
    }
}
