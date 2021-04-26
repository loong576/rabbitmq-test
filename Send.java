package com.my.maven.testmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Send {

    private final static String QUEUE_NAME = "hello loong576";//队列名

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.16.7.110");//服务器ip
        factory.setPort(5672);//端口
        factory.setUsername("admin");//登录名
        factory.setPassword("Admin123!");//密码
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message="hello world";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[x] Sent '"+message+"'");
        channel.close();
        connection.close();
    }
}