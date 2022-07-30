package com.baixiaozheng.rocketmq.producer.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProducerUtil {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 同步发送
     * 有默认重试机制，默认重试次数2次，如果发送失败，尝试不同的broker继续发送
     * @param tag
     * @param message
     * @return
     */
    public Boolean syncSend(String tag, Object message){
        SendResult sendResult = null;
        sendResult = rocketMQTemplate.syncSend(tag, message);
        if(SendStatus.SEND_OK.equals(sendResult.getSendStatus())){
            return true;
        } else {
            log.error("Failed to syncSend, message: {}", JSONObject.toJSONString(message));
            return false;
        }
    }

    /**
     * 异步发送
     * 有默认重试机制，默认重试次数2次，如果发送失败，继续尝试当前broker发送
     * @param tag
     * @param message
     * @return
     */
    public Boolean asyncSend(String tag, Object message){
        Boolean result = true;
        rocketMQTemplate.asyncSend(tag, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("success to asyncSend, message: {}", JSONObject.toJSONString(message));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("Failed to asyncSend, message: {}", JSONObject.toJSONString(message));
            }
        });
        return result;
    }

    /**
     * 只管发送，不管发送结果
     * @param tag
     * @param message
     */
    public void oneWaySend(String tag, Object message){
        rocketMQTemplate.sendOneWay(tag,message);
    }
}
