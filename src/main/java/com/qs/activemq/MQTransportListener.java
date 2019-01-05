package com.qs.activemq;

import org.apache.activemq.transport.TransportListener;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MQTransportListener implements TransportListener {

    private static Logger logger = Logger.getLogger(MQTransportListener.class);

    private static boolean flag = true;

    public static boolean IsException(){
        return flag;
    }


    @Override
    public void onCommand(Object o) {
        logger.info("onCommand检测到服务端命令:{}");
    }

    @Override
    public void onException(IOException e) {
        flag = false;
        logger.error("onException -> 消息服务器连接错误......");
    }

    @Override
    public void transportInterupted() {
        flag = false;
        logger.error("transportInterupted -> 消息服务器连接发生中断......");
    }

    @Override
    public void transportResumed() {
        flag = true;
        logger.info("transportResumed -> 消息服务器连接已恢复......");
    }
}
