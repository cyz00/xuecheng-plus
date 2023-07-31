package com.xuecheng.checkcode.controller;

import com.xuecheng.checkcode.model.CheckCodeParamsDto;
import com.xuecheng.checkcode.model.CheckCodeResultDto;
import com.xuecheng.checkcode.service.CheckCodeService;
import com.xuecheng.checkcode.service.SendCheckCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.M
 * @version 1.0
 * @description 验证码服务接口
 * @date 2022/9/29 18:39
 */
@Api(value = "验证码服务接口")
@RestController
@Slf4j
public class CheckCodeController {
    @Autowired
    CheckCodeService checkCodeService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    SendCheckCodeService sendCheckCodeService;

    @Resource(name = "PicCheckCodeService")
    private CheckCodeService picCheckCodeService;


    @ApiOperation(value="生成验证信息", notes="生成验证信息")
    @PostMapping(value = "/pic")
    public CheckCodeResultDto generatePicCheckCode(CheckCodeParamsDto checkCodeParamsDto){
        return picCheckCodeService.generate(checkCodeParamsDto);
    }

    @ApiOperation(value="校验", notes="校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "业务名称", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "key", value = "验证key", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType="query")
    })
    @PostMapping(value = "/verify")
    public Boolean verify(String key, String code){
        Boolean isSuccess = picCheckCodeService.verify(key,code);
        return isSuccess;
    }
//    @ApiOperation(value="发送验证码", notes="发送验证信息")
//    @PostMapping(value = "/phone")
//    public void SendCheckCode(@RequestParam("param1") String phone){
////        String code = redisTemplate.opsForValue().get(phone);
////        if(!StringUtils.isEmpty(code)){
////            return;
////        }
//        // 如果redis获取不到，进行阿里云发送
//        //生成随机数
//        String code = sendCheckCodeService.generate(6);
//        Map map = new HashMap();
//        map.put("code",code);
//        boolean b = sendCheckCodeService.sendPhoneCheckCode(phone,map);
//        if (b){
//            //如果发送成功，就把验证码存到redis里，设置5分钟有效时间
//            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
//            log.debug("成功");
//            log.debug("验证码为:{}",code);
////            System.out.println("验证码为"+code)；
//        }else {
//            log.debug("失败");
//        }
//    }
//    @ApiOperation(value = "发送邮箱验证码", tags = "发送邮箱验证码")
//    @PostMapping("/phone")
//    public void sendEMail(@RequestParam("param1") String email) {
//        String code = MailUtil.achieveCode();
//        sendCheckCodeService.sendEMail(email, code);
//    }
    @ApiOperation(value = "发送验证码", tags = "发送验证码")
    @PostMapping("/phone")
    public void sendCode(@RequestParam("param1") String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            log.debug("验证码为:{}",code);
            return;
        }
        //生成随机数
        code = sendCheckCodeService.generate(6);
        redisTemplate.opsForValue().set(phone,code,1, TimeUnit.MINUTES);
        log.debug("成功");
        log.debug("验证码为:{}",code);
    }

}
