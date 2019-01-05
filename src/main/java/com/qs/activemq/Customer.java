package com.qs.activemq;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class Customer implements MessageListener {

    private static Logger logger = Logger.getLogger(Customer.class);

    public static ConcurrentLinkedDeque<Object> msgList = new ConcurrentLinkedDeque<>();

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message) {

            logger.info(Thread.currentThread().getName()+"------------receive to jms Start");
            ObjectMessage objectMessage = (ObjectMessage)message;
        try {
            Serializable receiveMsg = objectMessage.getObject();

            if(!msgList.contains(receiveMsg)){
                msgList.add(receiveMsg);
                redisTemplate.opsForValue().set("test",msgList,1000*60);
            }

            logger.info(Thread.currentThread().getName()+"从队列接收到了消息:《======================"+receiveMsg.toString()+":已收到！");
            logger.info(Thread.currentThread().getName()+"------------receive to jms End");
        } catch (JMSException e) {
            e.printStackTrace();
        }



    }

    public ConcurrentLinkedDeque<Object> getMsgList() {
        return msgList;
    }

    //    //接受消息
//    public TextMessage receiveMessage(Destination destination){
//        TextMessage textMessage = null;
//        long now = System.currentTimeMillis();
//        long later  = now + 10*1000;
//        while (true){
//            textMessage = (TextMessage) jmsTemplate.receive(destination);
//
//            if(System.currentTimeMillis() >= later){
//                break;
//            }
//        }
//
//        try {
//            logger.info(Thread.currentThread().getName()+"从队列"+destination.toString()+"接收到了消息:《======================"+textMessage.getText());
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//
//        return  textMessage;
//    }

}
