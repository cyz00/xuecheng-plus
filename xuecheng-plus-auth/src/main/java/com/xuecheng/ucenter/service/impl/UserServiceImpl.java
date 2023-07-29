package com.xuecheng.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.ucenter.mapper.XcMenuMapper;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.dto.AuthParamsDto;
import com.xuecheng.ucenter.model.dto.XcUserExt;
import com.xuecheng.ucenter.model.po.XcMenu;
import com.xuecheng.ucenter.model.po.XcUser;
import com.xuecheng.ucenter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zcy
 * @version 1.0
 * @description 自定义UserDetailsService
 * @date 2023/7/18
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    XcUserMapper xcUserMapper;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    XcMenuMapper xcMenuMapper;


    /**
     * @description 根据账号查询用户信息
     * @param s  账号
     * @return org.springframework.security.core.userdetails.UserDetails
     * @author zcy
     * @date 2023/7/17
     */
    //传入的请求认证的参数就是AuthParamsDto
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //将出传入的json转成AuthParamsDto对象
        AuthParamsDto authParamsDto = null;
        try {
            //将认证参数转为AuthParamsDto类型
            authParamsDto = JSON.parseObject(s, AuthParamsDto.class);
        } catch (Exception e) {
            log.info("认证请求不符合项目要求:{}",s);
            throw new RuntimeException("认证请求数据格式不对");
        }
        //认证类型
        String authType = authParamsDto.getAuthType();
        //根据认证类型从从spring容器中取出指定的bean
        String beanName = authType + "_authservice";
        AuthService authService = applicationContext.getBean(beanName,AuthService.class);
        //调用认证
        XcUserExt xcUserExt = authService.execute(authParamsDto);
        UserDetails userDetails = getUserPrincipal(xcUserExt);

//        //账号
//        String username = authParamsDto.getUsername();
//
//        XcUser user = xcUserMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getUsername, username));
//        if(user==null){
//            //返回空表示用户不存在
//            return null;
//        }
//        //取出数据库存储的正确密码，最终封装成一个UserDetails对象给spring security框架返回，由框架进行密码比对
//        String password  =user.getPassword();
//        //用户权限,如果不加报Cannot pass a null GrantedAuthority collection
//        String[] authorities= {"test"};
//        //为了安全在令牌中不放密码
//        user.setPassword(null);
//        //将user对象转json
//        String userString = JSON.toJSONString(user);
//        //创建UserDetails对象,权限信息待实现授权功能时再向UserDetail中加入
//        UserDetails userDetails = User.withUsername(userString).password(password).authorities(authorities).build();

        return userDetails;
    }
    public UserDetails getUserPrincipal(XcUserExt user){
        String[] authorities= {"test"};
        String password  =user.getPassword();
        //根据用户id查询权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(user.getId());
        if(xcMenus.size()>0){
            List<String> permissions = new ArrayList<>();
            xcMenus.forEach(m->{
                //拿到用户拥有的权限标识
                permissions.add(m.getCode());
            });
            authorities = permissions.toArray(new String[0]);
        }
        //为了安全在令牌中不放密码
        user.setPassword(null);
        //将user对象转json
        String userString = JSON.toJSONString(user);
        //创建UserDetails对象,权限信息待实现授权功能时再向UserDetail中加入
        UserDetails userDetails = User.withUsername(userString).password(password).authorities(authorities).build();
        return userDetails;
    }


}
