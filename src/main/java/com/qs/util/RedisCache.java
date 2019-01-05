package com.qs.util;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

public class RedisCache implements Cache<String,Object> {

    private RedisManager cache;

    public RedisCache (RedisManager redisManager){
        this.cache = redisManager;
    }

    @Override
    public Object get(String o) throws CacheException {
        return cache.get(o.getBytes());
    }

    @Override
    public Object put(String o, Object o2) throws CacheException {
        return cache.set(o.getBytes(),o2.toString().getBytes());
    }

    @Override
    public Object remove(String o) throws CacheException {
        Object value = cache.get(o.getBytes());
        cache.remove(o.getBytes());
        return value;
    }

    @Override
    public void clear() throws CacheException {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public Set keys() {
        return cache.keys();
    }

    @Override
    public Collection values() {
        return cache.values();
    }
}
