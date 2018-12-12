package com.kbp.quickstart;



import com.kbp.constants.Const;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class Producer {

	public static void main(String[] args) throws Exception {

		DefaultMQProducer producer = new DefaultMQProducer("test_quick_producer_name");

		producer.setNamesrvAddr(Const.NAMESRV_ADDR_MASTER_SLAVE);

		producer.start();

		for (int i = 0; i <5; i++) {
			Message message = new Message("test_quick_topic" ,
					      "TagA",
					      "key" + i ,
					       ("Hello RocketMq" + i).getBytes()  );
			/*SendResult status = producer.send(message);
			System.err.println(status);*/
           /* if( i == 1){
            	message.setDelayTimeLevel(3);
			}*/

            SendResult sr = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message message, Object arg) {

                    Integer queuenumber = (Integer) arg;
                    return mqs.get(queuenumber);
                }
            }, 2);
            System.out.println(sr);

			//异步发送消息
			producer.send(message, new SendCallback() {
				@Override
				public void onSuccess(SendResult sendResult) {
					System.out.println("msgid" + sendResult.getMsgId() + " ,status" + sendResult.getSendStatus());
				}

				@Override
				public void onException(Throwable e) {
                      e.printStackTrace();
					System.err.println("--------发送失败");
				}
			});
		}

		//producer.shutdown();

	}
}
