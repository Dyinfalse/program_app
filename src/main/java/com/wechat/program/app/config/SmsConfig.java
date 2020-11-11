package com.wechat.program.app.config;

import com.github.qcloudsms.SmsSingleSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {
    /**
     * 初始化主账号名称
     **/
    @Value("${smsconfig.appkey}")
    private String APP_KEY;

    /**
     * 初始化应用ID
     **/
    @Value("${smsconfig.appId}")
    private int APP_ID;

    @Bean
    public SmsSingleSender smsSingleSender() {
        return new SmsSingleSender(APP_ID, APP_KEY);
    }
}
