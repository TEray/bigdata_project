package com.example.demo.controller;

import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * echarts 图标专用控制器
 * 1.返回页面模板
 * 2.处理相应的查询请求
 */
@Controller
public class CommonController {

    @Autowired
    private IUserService userService;

    @RequestMapping("bigdata/{page}")
    public String home(@PathVariable String page){

        return page;
    }

    @RequestMapping("/charts")
    public String index(){

        return "charts";
    }

    @ResponseBody
    @RequestMapping(value = "/charts/data/{method}",method = RequestMethod.POST,consumes = {"application/json"})
    public Object data(@PathVariable String method){

        if ("1".equals(method)){
            return userService.chartdata1();
        }else if("2".equals(method)){
            return userService.chartdata2();
        }else if("3".equals(method)){
            return userService.chartdata3();
        }else if("4".equals(method)){
            return userService.chartdata4();
        }else if("5".equals(method)){
            return userService.chartdata5();
        }
        return null;
    }
}
