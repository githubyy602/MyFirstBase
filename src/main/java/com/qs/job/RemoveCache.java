package com.qs.job;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RemoveCache {

    @Resource
    private RedisTemplate redisTemplate;

    //清除缓存
    public void remove(){
        redisTemplate.delete("test");
    }

}
