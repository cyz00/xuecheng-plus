package com.xuecheng.content.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zcy
 * @version 1.0
 * @description freemarker测试
 * @date 2023/7/12
 * @RestController 返回界面数据，@Controller返回界面
 */

@Controller
public class FreemarkerController {

    @GetMapping("/testfreemarker")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
        //设置模型数据
        modelAndView.addObject("name", "小明");
        //设置模板名称
        modelAndView.setViewName("test");
        return modelAndView;
    }
}
