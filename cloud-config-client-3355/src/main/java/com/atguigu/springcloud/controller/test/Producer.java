package com.atguigu.springcloud.controller.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
            private final static String QUEUE_NAME = "hello";
            public static void main(String[] args) throws IOException, TimeoutException {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setUsername("admin");
                factory.setPassword("admin");
                factory.setHost("localhost");
                factory.setPort(5672);
                factory.setVirtualHost("/");

                Connection connection = factory.newConnection();

                Channel channel = connection.createChannel();
                /**
                 * 生成一个queue队列
                 * 1、队列名称 QUEUE_NAME
                 * 2、队列里面的消息是否持久化(默认消息存储在内存中)
                 * 3、该队列是否只供一个Consumer消费 是否共享 设置为true可以多个消费者消费
                 * 4、是否自动删除 最后一个消费者断开连接后 该队列是否自动删除
                 * 5、其他参数
                 */
                channel.queueDeclare(QUEUE_NAME,false,false,false,null);
                String message = "Hello world!";
                /**
                 * 发送一个消息
                 * 1、发送到哪个exchange交换机
                 * 2、路由的key
                 * 3、其他的参数信息
                 * 4、消息体
                 */
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                System.out.println(" [x] Sent '"+message+"'");

                channel.close();
                connection.close();
            }
}
