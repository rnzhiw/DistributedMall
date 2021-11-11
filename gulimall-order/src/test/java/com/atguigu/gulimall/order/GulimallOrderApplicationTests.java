package com.atguigu.gulimall.order;

import com.atguigu.gulimall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallOrderApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessageTest() {

        for(int i = 0;i < 10;i++) {
            OrderReturnReasonEntity orderReturnReasonEntity = new OrderReturnReasonEntity();
            orderReturnReasonEntity.setId(1L);
            orderReturnReasonEntity.setCreateTime(new Date());
            orderReturnReasonEntity.setName("哈哈" + i);
            String msg = "hello world";
            rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",orderReturnReasonEntity);
            log.info("消息发送完成{}",orderReturnReasonEntity);
        }


    }

    /**
     * 1. 如何创建exchange[hello-java-exchange],Queue,Binding
     *      1) 使用AmqpAdmin进行创建
     * 2. 如何收发消息
     */
    @Test
    public void createExchange() {
        //amqpAdmin
        //exchange:String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        DirectExchange directExchange = new DirectExchange("hello-java-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange[{}]创建成功","hello-java-exchange");
    }

    @Test
    public void createQueue() {

        Queue queue = new Queue("hello-java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        log.info("Queue[{}]创建成功","hello-java-queue");
    }

    @Test
    public void createBinding() {
        // String destination[目的地],
        // Binding.DestinationType destinationType[目的地类型],
        // String exchange[交换机],
        // String routingKey[路由键],
        // Map<String, Object> arguments[参数]
        Binding binding = new Binding("hello-java-queue",
                Binding.DestinationType.QUEUE,
                "hello-java-exchange",
                "hello.java",
                null);
        amqpAdmin.declareBinding(binding);
        log.info("Binding[{}]创建成功","hello-java-binding");
    }

}
