package com.qs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qs.activemq.Customer;
import com.qs.activemq.MQTransportListener;
import com.qs.activemq.Producer;
import com.qs.entity.User;
import com.qs.service.IUserService;
import com.qs.util.MemcachedUtils;
import com.qs.util.ResponseMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 游客控制器
 */
@Controller
@RequestMapping("tourist")
public class TouristController {

    private static Logger logger = Logger.getLogger(TouristController.class);

    @Resource
    private IUserService userService;

    @Resource
    private Producer producer;

    @Resource
    private Customer customer;

    @Resource
    private MemcachedUtils memcachedUtils;

    @Resource(name = "demoQueueDestination")
    private Destination destination;

    /**
     * 进入首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/index.do",method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response){

        return "index";
    }

    /**
     * 获取内容层的数据
     * @param url
     * @return
     */
    @RequestMapping(value = "/getContent.do",method = RequestMethod.GET)
    public  String getContent(String url){
        return "content";
    }


    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping(value = "/goLoginPage.do",produces = "text/html; charset=utf-8")
    public  String goLoginPage(HttpServletResponse response){
        return "login";
    }

    /**
     * 用户注册
     * @param userName
     * @param password
     * @param password2
     * @param birth
     * @param sex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register.do",method = RequestMethod.POST,produces = "text/html;charset=utf-8")
    public String register(@RequestParam("userName") String userName,
                           @RequestParam("pwd") String password,
                           @RequestParam("pwd2") String password2,
                           @RequestParam(value = "birth",required = false) String birth,
                           @RequestParam(value = "sex",required = false) int sex){

        Map<String,Object> reusltMap = ResponseMap.getResultMap();

        if(!StringUtils.equals(password,password2)){
            reusltMap.put(ResponseMap.STATUS,"failure");
            reusltMap.put(ResponseMap.MESSAGE,"两次输入的密码不一致");
            return JSON.toJSONString(reusltMap);
        }

        try {
            User user = new User();
            user.setUsername(userName);
            user.setPassword(password);
            user.setBirthday(birth);
            user.setSex(sex);
            user.setAddress("");

            //添加用户
            int num = userService.insert(user);

            if(num > 0){
                reusltMap.put(ResponseMap.STATUS,"success");
            }
            else{
                reusltMap.put(ResponseMap.STATUS,"failure");
                reusltMap.put(ResponseMap.MESSAGE,"用户注册失败");
            }

            return JSON.toJSONString(reusltMap);
        }catch (Exception e){
            logger.error("tourist/register.do has error>>>>>>:"+e);
            e.printStackTrace();
            reusltMap.put(ResponseMap.STATUS,"failure");
            reusltMap.put(ResponseMap.MESSAGE,"注册发生异常");
            return JSON.toJSONString(reusltMap);
        }

    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login.do",method = RequestMethod.POST,produces = "text/html;charset=utf-8")
    public String login(@RequestParam("username") String username,
                        @RequestParam("pwd") String password){

        Map<String,Object> resultMap = ResponseMap.getResultMap();

        //获取Subject对象,在获取该对象时控制器必须经过shiro的拦截，否则会报空指针
        Subject subject = SecurityUtils.getSubject();
        //将账户信息存入token

        UsernamePasswordToken token = null;

        try {

            token = new UsernamePasswordToken(username,password);
            subject.login(token);

            resultMap.put(ResponseMap.STATUS,"success");
            resultMap.put(ResponseMap.MESSAGE,"登录验证成功");
            return JSON.toJSONString(resultMap);

        } catch (UnknownAccountException e) {
            resultMap.put(ResponseMap.STATUS,"failure");
            resultMap.put(ResponseMap.MESSAGE,"没有此帐号,登录失败");
            e.printStackTrace();
            return JSON.toJSONString(resultMap);
        }catch (IncorrectCredentialsException e){
            resultMap.put(ResponseMap.STATUS,"failure");
            resultMap.put(ResponseMap.MESSAGE,"密码错误");
            e.printStackTrace();
            return JSON.toJSONString(resultMap);
        }catch (LockedAccountException e){
            resultMap.put(ResponseMap.STATUS,"failure");
            resultMap.put(ResponseMap.MESSAGE,"账号已锁定");
            e.printStackTrace();
            return JSON.toJSONString(resultMap);
        }catch (DisabledAccountException e){
            resultMap.put(ResponseMap.STATUS,"failure");
            resultMap.put(ResponseMap.MESSAGE,"账户禁用");
            e.printStackTrace();
            return JSON.toJSONString(resultMap);
        }catch (ExcessiveAttemptsException e){
            resultMap.put(ResponseMap.STATUS,"failure");
            resultMap.put(ResponseMap.MESSAGE,"登录重试次数以达到上限");
            e.printStackTrace();
            return JSON.toJSONString(resultMap);
        }catch (AccountException e){
            resultMap.put(ResponseMap.STATUS,"failure");
            resultMap.put(ResponseMap.MESSAGE,"账户异常");
            e.printStackTrace();
            return JSON.toJSONString(resultMap);
        }catch (ExpiredCredentialsException e){
            resultMap.put(ResponseMap.STATUS,"failure");
            resultMap.put(ResponseMap.MESSAGE,"账户凭证已过期");
            e.printStackTrace();
            return JSON.toJSONString(resultMap);
        }catch (AuthenticationException e){
            resultMap.put(ResponseMap.STATUS,"failure");
            resultMap.put(ResponseMap.MESSAGE,"账户验证失败");
            e.printStackTrace();
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 判断是否登录
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkLogin.do",method = RequestMethod.POST,produces = "text/html;charset=utf-8")
    public String checkLogin(){

        Map<String,Object> resultMap = ResponseMap.getResultMap();

        Subject subject = SecurityUtils.getSubject();
        boolean flag = subject.isAuthenticated();

//        Object user = subject.getSession().getAttribute("currentUser");

        if(flag){
            resultMap.put(ResponseMap.STATUS,ResponseMap.SUCCESS_FLAG);
            User user = (User)subject.getPrincipal();
            resultMap.put(ResponseMap.MESSAGE,user.getUsername());
        }else {
            resultMap.put(ResponseMap.STATUS,ResponseMap.FAILURE_FLAG);
            resultMap.put(ResponseMap.MESSAGE,"未登录");
        }

        return JSON.toJSONString(resultMap);
    }


    /*===============================================activeMQ================================================*/
    @ResponseBody
    @RequestMapping(value = "/sendMsg.do",method = RequestMethod.POST)
    public void sendMsg(){
        Map<String,Object> resultMap = new HashMap<>();
        logger.info(Thread.currentThread().getName()+"------------send to jms Start");
        String testMsg = "这是一条队列测试消息，收到了嘛？";

        producer.sendMessage(testMsg);
        logger.info(Thread.currentThread().getName()+"------------send to jms End");

        resultMap.put("result","success");
        //return  resultMap;
    }

    //    @ResponseBody
//    @RequestMapping(value = "/receiveMsg.do",method = RequestMethod.POST)
//    public Map<String,Object>  receiveMsg(){
//        Map<String,Object> resultMap = new HashMap<>();
//        logger.info(Thread.currentThread().getName()+"------------receive from jms Start");
//        customer.receiveMessage(destination);
//        logger.info(Thread.currentThread().getName()+"------------receive from jms End");
//        resultMap.put("result","success");
//        return  resultMap;
//    }


    @ResponseBody
    @RequestMapping(value = "secondsKillActive.do",method = RequestMethod.POST)
    public Object secondsKillActive(String id,String name){
        Map<String,Object>  resMap = new HashMap<>();

        JSONObject obj = new JSONObject();
        obj.put("id",UUID.randomUUID());
        obj.put("name",name);
        System.out.println("您当前的IP为："+id+",地址为："+name);

        ConcurrentLinkedDeque<Object> temp = customer.getMsgList();

        if(temp.size() >= 5){
            resMap.put("success",false);
            resMap.put("msg","秒杀结束！");
            return resMap;
        }
        else if(temp.contains((Serializable)obj)){
            resMap.put("success",false);
            resMap.put("msg","你已抢到优惠券！");
            return resMap;
        }else {

            try {
                producer.sendMessage((Serializable)obj);
                Thread.sleep(1000);

                if(MQTransportListener.IsException()){

                    System.out.println("当前消息数："+temp.size());

                    if(temp.contains((Serializable)obj)){
                        resMap.put("success",true);
                        resMap.put("msg","恭喜抢券成功！");
                        return resMap;
                    }else{
                        resMap.put("success",true);
                        resMap.put("msg","运气不佳，未能抢到！");
                        return resMap;
                    }



                }else{
                    resMap.put("success",false);
                    resMap.put("msg","发生异常，请稍后！");
                    return resMap;
                }
            } catch (InterruptedException e) {
                resMap.put("success",false);
                resMap.put("msg","error异常");
                e.printStackTrace();
                return resMap;
            }

        }

    }




    /*===============================================activeMQ================================================*/

    /*===============================================MemcacheTest================================================*/

    @ResponseBody
    @RequestMapping(value = "/getMemcacheValue.do",method = RequestMethod.POST)
    public void getMemcacheValue(){
        memcachedUtils.set("test","avc11111");
        logger.info("获取到的缓存值为："+memcachedUtils.get("test").toString());
    }

    /*===============================================MemcacheTest================================================*/




}
