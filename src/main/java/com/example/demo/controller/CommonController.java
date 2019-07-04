package com.example.demo.controller;

import com.example.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * echarts 图标专用控制器
 * 1.返回页面模板
 * 2.处理相应的查询请求
 */
@Api("页面接口")
@Controller
public class CommonController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value="获取展示页面", notes="根据{page}获取展示页面")
    @ApiImplicitParam(name = "page", value = "页面名称", required = true, dataType = "String")
    @GetMapping("bigdata/{page}")
    public String home(@PathVariable String page){

        return page;
    }


    @ResponseBody
    @PostMapping(value = "/charts/data/{method}",consumes = {"application/json"})
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
