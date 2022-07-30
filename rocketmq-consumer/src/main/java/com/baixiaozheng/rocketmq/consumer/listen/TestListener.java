package com.baixiaozheng.rocketmq.consumer.listen;

import com.baixiaozheng.rocketmq.common.RocketConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = RocketConstant.TOPIC,consumerGroup = "my-test-group")
@Slf4j
public class TestListener implements RocketMQListener<String> {


    /**
     * 消费消息，同样有默认的重试机制
     * @param message
     */
    @Override
    public void onMessage(String message) {
        log.info("消费，收到消息:{}",message );
    }
}
