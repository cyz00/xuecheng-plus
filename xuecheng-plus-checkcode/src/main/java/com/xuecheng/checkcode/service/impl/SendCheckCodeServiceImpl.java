package com.xuecheng.checkcode.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.google.gson.Gson;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.checkcode.service.CheckCodeService;
import com.xuecheng.checkcode.service.SendCheckCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
//import com.xuecheng.checkcode.utils.MailUtil;
@Slf4j
@Service
public class SendCheckCodeServiceImpl implements SendCheckCodeService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    public boolean sendPhoneCheckCode(String phone,Map map) {
        if(StringUtils.isEmpty(phone)){
            throw new RuntimeException("请输入手机号");
        }
//        Config config = new Config()
//                .setAccessKeyId("LTAI5tMSsqQ1qFpWdbUoLK1S")
//                .accessKeySecret("Kdi5lW5KuAWqkj139cmlCXwTQDWEGM");
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId("LTAI5tMSsqQ1qFpWdbUoLK1S")
                // 您的AccessKey Secret	（这两个还不知道的去我前两次关于阿里云的有教程哪里找）
                .setAccessKeySecret("Kdi5lW5KuAWqkj139cmlCXwTQDWEGM");
        // 访问的域名（这个不用变都是这个）
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = null;
        try {
            client = new Client(config);
            SendSmsRequest request = new SendSmsRequest();

            request.setSignName("阿里云短信测试");//签名名称
            request.setTemplateCode("\n" +
                    "SMS_154950909");//模版Code
            request.setPhoneNumbers(phone);//电话号码
            //这里的参数是json格式的字符串
            request.setTemplateParam(JSONObject.toJSONString(map));
            SendSmsResponse response = client.sendSms(request);
            log.debug("短信发送成功:{}",new Gson().toJson(response));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
           throw new RuntimeException("短信发送失败");
        }

    }
    @Override
    public String generate(int len){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i=0;i<len;i++){
            int number=random.nextInt(9);
            sb.append(number);
        }
        return sb.toString();
    }

//    @Override
//    public void sendEMail(String email, String code) {
//        // 1. 向用户发送验证码
//        try {
//            MailUtil.sendTestMail(email, code);
//        } catch (MessagingException e) {
//            log.debug("邮件发送失败：{}", e.getMessage());
//            log.debug("发送验证码：{}", code);
//            XueChengPlusException.cast("发送验证码失败，请稍后再试");
//        }
//        // 2. 将验证码缓存到redis，TTL设置为2分钟
//        redisTemplate.opsForValue().set(email, code, CODE_TTL, TimeUnit.SECONDS);
//    }
}
