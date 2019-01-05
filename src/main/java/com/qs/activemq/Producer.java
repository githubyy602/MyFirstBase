package com.qs.activemq;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

/**
 * 消息生产者
 */
@Service
public class Producer {

    private static Logger logger = Logger.getLogger(Producer.class);

    @Resource
    private JmsTemplate jmsTemplate;

    //发送消息
    public void sendMessage(Destination destination,String msg){
        logger.info(Thread.currentThread().getName()+"向队列"+destination.toString()+"发送了消息:===================================》"+msg);
        jmsTemplate.send(destination,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }


    public void sendMessage(String msg){
        Destination destination = jmsTemplate.getDefaultDestination();
        logger.info(Thread.currentThread().getName()+"向队列默认消息队列"+destination.toString()+"发送了消息:===================================》"+msg);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });

    }

    public void sendMessage(Serializable obj){
        Destination destination = jmsTemplate.getDefaultDestination();
        logger.info(Thread.currentThread().getName()+"向队列默认消息队列"+destination.toString()+"发送了消息:===================================》"+obj.toString());
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(obj);
            }
        });
    }


}
