package com.qs.controller;

import com.alibaba.fastjson.JSON;
import com.qs.util.ResponseMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 登录用户控制器
 */
@Controller
@RequestMapping("user")
public class UserController {

    /**
     * 用户注销
     */
    @ResponseBody
    @RequestMapping(value = "/logout.do",method = RequestMethod.POST,produces = "text/html;charset=utf-8")
    public String logout(){
        Map<String,Object> resultMap = ResponseMap.getResultMap();

        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            resultMap.put(ResponseMap.STATUS,ResponseMap.SUCCESS_FLAG);
        } catch (Exception e) {
            resultMap.put(ResponseMap.STATUS,ResponseMap.FAILURE_FLAG);
            resultMap.put(ResponseMap.MESSAGE,"注销用户异常");
            e.printStackTrace();
        }

        return JSON.toJSONString(resultMap);

    }

    /**
     * 进入内容详情页
     * @return
     */
    @RequestMapping(value = "/gotoDetail.do")
    public  String gotoDetail(){
        return  "single";
    }

}
