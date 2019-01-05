package com.qs.util;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class RedisManager {
    //引入redis缓存连接池
    private JedisPool jedisPool;
    //redis客户端ip
    private String host ="127.0.0.1";
    //redis客户端端口号
    private int port = 6379;
    //链接密码
    private String password = "";
    //缓存连接超时
    private int timeout = 1000;
    //缓存时效
    private int expire = 7200;

    //初始化连接池
    public void init(){
        if (jedisPool == null){
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(100);
            config.setMinIdle(10);
            config.setMaxTotal(100);
            config.setMaxWaitMillis(1000);

            if (password != null && !StringUtils.isBlank(password)){
                jedisPool = new JedisPool(config,host,port,timeout,password);
            }else if (timeout != 0){
                jedisPool = new JedisPool(config,host,port,timeout,null);
            }else{
                jedisPool = new JedisPool(config,host,port,Protocol.DEFAULT_TIMEOUT,null);
            }
        }

    }

    /**
     * 添加缓存
     * @param key
     * @param value
     */
    public byte[] set(byte [] key,byte [] value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,value);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return  value;
    }

    /**
     * 添加带有时效的缓存
     * @param key
     * @param value
     * @param expire
     */
    public byte [] set(byte [] key,byte [] value,int expire){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,value);
            if (expire != 0){
                jedis.expire(key,expire);
            }

        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

        return value;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public byte [] get(byte [] key){
        Jedis jedis = null;
        byte [] value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return  value;
    }

    /**
     * 删除指定缓存
     * @param key
     */
    public void remove(byte [] key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

    }

    /**
     * 清空缓存
     * @return
     */
    public void clear(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.flushDB();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 获取缓存size
     * @return
     */
    public int size(){
        Jedis jedis = null;
        int num = 0;
        try {
            jedis = jedisPool.getResource();
            num = jedis.dbSize().intValue();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

        return num;
    }

    /**
     * 获取所有缓存key
     * @return
     */
    public Set keys(){
        Jedis jedis = null;
        Set res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.keys(StringUtils.EMPTY);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

        return res;
    }

    /**
     * 获取所有缓存value
     * @return
     */
    public Collection values(){
        Jedis jedis = null;
        List values = new ArrayList();
        try {
            jedis = jedisPool.getResource();
            //匹配所有
            Set keys = jedis.keys("*");

            for (int i = 0; i < keys.size(); i++) {
                values.add(jedis.get(keys.toArray()[i].toString().getBytes()));
            }

        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

        return values;
    }




    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
