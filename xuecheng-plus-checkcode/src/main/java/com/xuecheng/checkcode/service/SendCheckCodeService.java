package com.xuecheng.checkcode.service;

import java.util.Map;

public interface SendCheckCodeService {
    /**
     * @description 生成短信验证码
     * @param phone 生成验证码参数
     * @return com.xuecheng.checkcode.model.CheckCodeResultDto 验证码结果
     * @author zcy
     * @date 2023/7/31
     */
    boolean sendPhoneCheckCode(String phone, Map map);
    public String generate(int len);

//    void sendEMail(String email, String code);
}
