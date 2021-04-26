package com.my.maven.testmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;

public class Recv {

    private final static String QUEUE_NAME = "hello loong576";//队列名

    public static void main(String[] argv) throws Exception {
        //下面的配置与生产者相对应
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("172.16.7.110");//服务器ip
        factory.setPort(5672);//端口
        factory.setUsername("admin");//登录名
        factory.setPassword("Admin123!");//密码
        Connection connection=factory.newConnection();//连接
        Channel channel=connection.createChannel();//频道
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);//队列
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //defaultConsumer实现了Consumer，我们将使用它来缓存生产者发送过来储存在队列中的消息。当我们可以接收消息的时候，从中获取。
        Consumer consumer=new DefaultConsumer(channel){
             @Override
              public void handleDelivery(String consumerTag, Envelope envelope,
                                         AMQP.BasicProperties properties, byte[] body)
                  throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
              }
        };
        //接收到消息以后，推送给RabbitMQ，确认收到了消息。
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}