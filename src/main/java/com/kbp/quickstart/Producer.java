package com.kbp.quickstart;



import com.kbp.constants.Const;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.List;

public class Producer {

	public static void main(String[] args) throws Exception {

		DefaultMQProducer producer = new DefaultMQProducer("test_quick_producer_name");

		producer.setNamesrvAddr(Const.NAMESRV_ADDR_SINGLE);

		producer.start();

		for (int i = 0; i <5; i++) {
			Message message = new Message("test_quick_topic" ,
					      "TagA",
					      "key" + i ,
					       ("Hello RocketMq" + i).getBytes()  );
			SendResult status = producer.send(message);
			System.err.println(status);
		}

		producer.shutdown();

	}
}
