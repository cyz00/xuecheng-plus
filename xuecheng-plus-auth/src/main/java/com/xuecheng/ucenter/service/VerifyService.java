package com.xuecheng.ucenter.service;

import com.xuecheng.ucenter.model.dto.FindPswDto;
import com.xuecheng.ucenter.model.dto.RegisterDto;

/**
 * @author zcy
 * @version 1.0
 * @description 找回密码
 * @date 2023/7/29
 */
public interface VerifyService {

    void findPassword(FindPswDto findPswDto);

    void register(RegisterDto registerDto);
}
