package com.xuecheng.ucenter.model.dto;

import lombok.Data;

@Data
public class FindPswDto {
    private String cellphone;//手机号
    private String email;
    private String checkcode;//验证码
    private String confirmpwd; //确认密码
    private String password; //密码
}
