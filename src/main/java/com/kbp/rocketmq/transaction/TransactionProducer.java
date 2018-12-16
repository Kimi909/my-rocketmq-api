package com.kbp.rocketmq.transaction;

import com.kbp.constants.Const;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * Created by kbp1234 on 2018/12/16.
 */
public class TransactionProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, InterruptedException {
        TransactionMQProducer producer = new TransactionMQProducer("test_tx_producer");
        ExecutorService executorService  = new ThreadPoolExecutor(2, 5, 100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {

                        Thread thread =new Thread(r);
                        thread.setName("test_tx_producer" + "check-thread");
                        return  thread;
                    }
                }
        );

        producer.setNamesrvAddr(Const.NAMESRV_ADDR_MASTER_SLAVE);
        producer.setExecutorService(executorService);

        TransactionListener transactionListener = new TransactionListenerImpl() ;

        producer.setTransactionListener(transactionListener);
        producer.start();

        Message message = new Message("test_tx_topic" ,"TagA","key",
                ("helloRocketmq 4 tx").getBytes(RemotingHelper.DEFAULT_CHARSET)  );
        producer.sendMessageInTransaction(message,"我是回调的参数");
         Thread.sleep(Integer.MAX_VALUE);
        //producer.shutdown();


    }
}
