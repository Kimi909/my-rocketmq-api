package com.kbp.rocketmq.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * Created by kbp1234 on 2018/12/16.
 */
public class TransactionListenerImpl  implements TransactionListener {
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
        System.out.println("------执行本地事务-----------");
        String callArg = (String) arg;
        System.err.println("callArg:" + callArg);
        System.err.println("msg:" + message);
       //tx.begin
            //数据库操作

        //tx.commit
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {

        System.out.println("------回调检查-----------" + msg);
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
