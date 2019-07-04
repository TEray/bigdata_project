package com.example.demo.controller;


import com.example.demo.service.ISparkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * spark控制器
 * 1.启动spark程序
 * 2.向kafka发送数据
 */
@RestController
@RequestMapping("/spark")
public class SparkController {

    @Autowired
    private ISparkService SparkService;

    @RequestMapping("pi")
    public String calPI(HttpServletRequest request) throws Exception{

        Map<Object,Object> paramMap = new HashMap<>();

        return SparkService.wordCount(paramMap);
    }
    @RequestMapping("kafka")
    public String kafka(HttpServletRequest request) throws Exception{

        Map<Object,Object> paramMap = new HashMap<>();

        return SparkService.kafkaSend(paramMap);
    }

    @PostMapping("any/{method}")
    public void any(@PathVariable String method) throws Exception{

        if ("1".equals(method)){
            SparkService.analyse4_1();
        }else if("2".equals(method)){
            SparkService.analyse4_2();
        }else if("3".equals(method)){
            SparkService.analyse4_3();
        }else if("4".equals(method)){
            SparkService.analyse4_4();
        }else if("5".equals(method)){
            SparkService.analyse4_5();
        }

    }
}
