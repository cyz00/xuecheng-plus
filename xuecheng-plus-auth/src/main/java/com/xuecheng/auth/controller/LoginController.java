package com.xuecheng.auth.controller;

import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.dto.FindPswDto;
import com.xuecheng.ucenter.model.dto.RegisterDto;
import com.xuecheng.ucenter.model.po.XcUser;
import com.xuecheng.ucenter.service.VerifyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.M
 * @version 1.0
 * @description 测试controller
 * @date 2022/9/27 17:25
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    XcUserMapper userMapper;
    @Autowired
    VerifyService verifyService;

    @RequestMapping("/login-success")
    public String loginSuccess() {

        return "登录成功";
    }


    @RequestMapping("/user/{id}")
    public XcUser getuser(@PathVariable("id") String id) {
        XcUser xcUser = userMapper.selectById(id);
        return xcUser;
    }

    @RequestMapping("/r/r1")
    @PreAuthorize("hasAuthority('p1')")//拥有p1权限方可访问
    public String r1() {
        return "访问r1资源";
    }

    @RequestMapping("/r/r2")
    @PreAuthorize("hasAuthority('p2')")//拥有p1权限方可访问
    public String r2() {
        return "访问r2资源";
    }

    @ApiOperation(value = "找回密码", tags = "找回密码")
    @PostMapping("/findpassword")
    public void findPassword(@RequestBody FindPswDto findPswDto) {
        verifyService.findPassword(findPswDto);
    }
    @ApiOperation(value = "注册账号",tags ="注册账号" )
    @PostMapping("/register")
    public void register(@RequestBody RegisterDto registerDto) {
        if(StringUtils.isNotEmpty(registerDto.getCellphone())){
            registerDto.setCheckcodekey(registerDto.getCellphone());
        }else{
            registerDto.setCheckcodekey(registerDto.getEmail());
        }
        verifyService.register(registerDto);
    }

}
