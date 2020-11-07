package com.zy.rocketmq.messagetransaction;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

public class TransactionProducer {
    public static final String TOPIC = "transactionTopic";
    public static final String TAGS = "transactionTags";

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        TransactionMQProducer producer = new TransactionMQProducer("transactionProducerGroup");
        producer.setNamesrvAddr("192.168.0.156:9876;192.168.0.156:9877");

        producer.start();

        // 未 commit 的 msg, consumer 是不可见的.
        producer.setTransactionListener(new TransactionListener() {

            /**
             * 在该方法中执行本地事务
             * @param msg
             * @param arg 这个 args 对应于 TransactionMQProducer#sendMessageInTransaction(final Message msg,  Object arg) 中的 arg
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                int i = (int) arg;
                if (i == 0 || i == 1) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (i == 2 || i == 3){
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.UNKNOW;
            }

            /**
             * 消息补偿机制:
             * 当本地提交了 msg, 但超过一定时间后始终没向 broker 发送 commit 或 rollback,
             * broker 会回调该方法, 该方法中执行完逻辑后, 最终向 broker 发送到底是 commit 还是 rollback 对应的 msg.
             * @param msg
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                // 这里根据本地事务情况, 进行 rollback 或者 commit.
                System.out.println("checkLocalTransaction: " + new String(msg.getBody(), StandardCharsets.UTF_8));
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        for (int i = 0; i < 5; i++) {
            Message message = new Message(TOPIC, TAGS, ("hello-world --> " + i).getBytes(StandardCharsets.UTF_8));
            SendResult result = producer.sendMessageInTransaction(message, i);
            System.out.println(result);
        }

        // 这里要回查, 不要停止了
        // producer.shutdown();

    }
}
