package com.ezcoding.config;

import com.github.qcloudsms.SmsMultiSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-04 13:34
 */
@Configuration
public class SmsConfig {

    @Value("${sms.appId}")
    private Integer appId;
    @Value("${sms.appKey}")
    private String appKey;
    @Value("${sms.smsSign}")
    private String smsSign;

    @Value("${sms.registerTemplateId}")
    private Integer registerTemplateId;

    @Bean
    public SmsMultiSender smsMultiSender() {
        return new SmsMultiSender(appId, appKey);
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Integer getRegisterTemplateId() {
        return registerTemplateId;
    }

    public void setRegisterTemplateId(Integer registerTemplateId) {
        this.registerTemplateId = registerTemplateId;
    }

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign;
    }

}
