package com.example.demo.controller;


import com.example.demo.service.IHbaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hbase 控制器
 * 1.查询hbase数据
 */
@RestController
@RequestMapping("/hbase")
public class HbaseController {

    private static final Logger logger = LogManager.getLogger("HbaseController");

    @Autowired
    private IHbaseService HbaseService;



    @RequestMapping("/{method}")
    public void Hbasetest(@PathVariable String method) throws Exception{

        logger.info("测试hbase-javaSpringBoot-整合");

        logger.info("method:"+method);

        if ("create".equalsIgnoreCase(method)){
            HbaseService.createtable();
        }else if("put".equalsIgnoreCase(method)){
            HbaseService.putData();
        }else if("get".equalsIgnoreCase(method)){
            HbaseService.getData();
        }
    }

}
