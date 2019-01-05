package com.qs.util;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeoutException;

/**
 * Xmemcached工具类
 */
@Service
public class MemcachedUtils {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private MemcachedClient memcachedClient;

    /**
     * 设置带时效的缓存
     * @param key
     * @param time
     * @param value
     */
    public boolean setExpire(String key,int time,Object value){

        boolean flag = false;

        try {
            flag = memcachedClient.set(key,time,value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    public boolean set(String key,Object value){
        boolean flag;
        flag = setExpire(key,0,value);
        return  flag;
    }

    /**
     * 添加带时效缓存
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setExpire(String key,Object value,int time){
        boolean flag = false;

        try {
            flag = memcachedClient.add(key,time,value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }

        return  flag;
    }

    /**
     * 添加缓存
     * @param key
     * @param value
     * @return
     */
    public boolean add(String key,Object value){
        return setExpire(key,0,value);
    }


    /**
     * 获取缓存值
     * @param key
     * @return
     */
    public Object get(String key){

        Object obj = null;

        try {
            obj = memcachedClient.get(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * 删除指定缓存
     * @param key
     */
    public  boolean delete(String key){

        boolean flag = false;

        try {
            flag = memcachedClient.delete(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 清除所有缓存
     */
    public void clear(){

        try {
            memcachedClient.flushAll();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }

    }




}
