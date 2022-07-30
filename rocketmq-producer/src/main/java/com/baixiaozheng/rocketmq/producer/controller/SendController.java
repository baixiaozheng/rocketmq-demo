package com.baixiaozheng.rocketmq.producer.controller;

import com.baixiaozheng.rocketmq.common.RocketConstant;
import com.baixiaozheng.rocketmq.producer.util.ProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SendController {

    @Autowired
    private ProducerUtil producerUtil;

    @PostMapping("/syncSend")
    public String syncSend( String message) {
        Boolean result = producerUtil.syncSend(RocketConstant.TOPIC+":"+RocketConstant.SYNC_TAG, message);
        if(result){
            return "发送成功";
        } else {
            return "发送失败";
        }
    }

    @PostMapping("/asyncSend")
    public String asyncSend( String message) {
        Boolean result = producerUtil.asyncSend(RocketConstant.TOPIC+":"+RocketConstant.ASYNC_TAG, message);
        if(result){
            return "发送成功";
        } else {
            return "发送失败";
        }
    }

    @PostMapping("/oneWaySend")
    public String oneWaySend( String message) {
        try {
            producerUtil.oneWaySend(RocketConstant.TOPIC+":"+RocketConstant.ONE_WAY_TAG, message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "发送失败";
        }

    }
}
