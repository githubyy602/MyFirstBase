package com.qs.util;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RedisSessionDao extends AbstractSessionDAO {

    private static Logger logger = Logger.getLogger(RedisSessionDao.class);

    private RedisManager redisManager;

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
        this.redisManager.init();
    }

    /**
     * 将session存入缓存
     * @param session
     */
    private void saveSession(Session session){
        if(session == null || session.getId() == null){
            logger.error("RedisSessionDao.saveSession中session为空或sessionid为空");
            return;
        }

        byte [] key = session.getId().toString().getBytes();
        byte [] value = SerializeUtils.serialize(session);
        session.setTimeout(redisManager.getExpire()*1000);
        redisManager.set(key,value);
    }

    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session,sessionId);
        //创建session的时候将该session存入缓存
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionid) {
        if(sessionid == null){
            logger.error("RedisSessionDao.doReadSession中session id is null");
            return null;
        }

        Session session = (Session)SerializeUtils.deserialize(redisManager.get(sessionid.toString().getBytes()));

        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        redisManager.remove(session.getId().toString().getBytes());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();

        Set<byte[]> keySet = redisManager.keys();

        for (byte[] key : keySet) {
            sessions.add((Session)SerializeUtils.deserialize(redisManager.get(key)));
        }

        return sessions;
    }
}
