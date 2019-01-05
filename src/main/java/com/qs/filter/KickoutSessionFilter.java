package com.qs.filter;

import com.qs.entity.User;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 *
 */
public class KickoutSessionFilter extends AccessControlFilter {

    //踢出后的页面地址
    private String url;
    //同一个账号最大会话数，默认为1
    private int maxSessionCount = 1;
    //踢出前者还是后者，默认踢出前者
    private boolean kickoutBefore = true;
    //会话管理
    private SessionManager sessionManager;
    //缓存
    private Cache<String,Deque<Serializable>> cache;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMaxSessionCount() {
        return maxSessionCount;
    }

    public void setMaxSessionCount(int maxSessionCount) {
        this.maxSessionCount = maxSessionCount;
    }

    public boolean isKickoutBefore() {
        return kickoutBefore;
    }

    public void setKickoutBefore(boolean kickoutBefore) {
        this.kickoutBefore = kickoutBefore;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager){
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }

    /**
     * 允许通过时进入
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * 拒绝通过时进入（如果返回false则按方法中自定义逻辑处理，返回true则按默认处理）
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        Subject subject = getSubject(servletRequest,servletResponse);
        //没有登录或是没有记住我设置则直接跳过
        if(!subject.isAuthenticated() && !subject.isRemembered()){
            return true;
        }

        User user = (User)subject.getPrincipal();
        String userName = user.getUsername();
        Session session = subject.getSession();
        Serializable sessionId  = session.getId();

        //根据用户名初始化队列
        Deque<Serializable> deque = cache.get(userName);
        if(deque == null){
            deque = new LinkedList<Serializable>();
            cache.put(userName,deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null){
            deque.add(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSessionCount){
            //被踢出的sessionId
            Serializable kickoutSessinId = null;
            //如果队列中sessionid大于指定上限，则开始踢人
            if(kickoutBefore){
                //踢出先前登录的用户
                kickoutSessinId = deque.removeFirst();
            }
            else {
                kickoutSessinId = deque.removeLast();
            }

            try {
                //给要踢出的会话添加kickout
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessinId));

                if(kickoutSession != null){
                    kickoutSession.setAttribute("kickout",true);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //踢出session中带有kickout属性的用户
        if(session.getAttribute("kickout") != null){

            try {
                subject.logout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //跳转到踢出后的页面
            WebUtils.issueRedirect(servletRequest,servletResponse,url);
            return  false;
        }

        return true;
    }
}
