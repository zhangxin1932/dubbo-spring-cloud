package com.zy.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConsumerPull {
    public static void main(String[] args) throws MQClientException {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("rocketmq-zy-consumer-group-push01");
        consumer.setNamesrvAddr("192.168.0.156:9876");

        // 消费该 topic 下所有 tags
        // consumer.subscribe("rocketmq-zy-topic1", "*");
        // 按照 tags 过滤
        consumer.subscribe("rocketmq-zy-topic1", "rocketmq-zy-t1-tags1 || rocketmq-zy-t1-tags1");
        consumer.start();

        while (true) {
            List<MessageExt> msgs = consumer.poll();
            msgs.forEach(e -> System.out.println(new String(e.getBody(), StandardCharsets.UTF_8) + "\t" + e));
        }
    }
}