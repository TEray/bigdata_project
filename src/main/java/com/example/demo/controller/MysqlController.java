package com.example.demo.controller;


import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * mysql控制器
 * 1.查询mysql数据
 */
@RestController
@RequestMapping("/mysql")
public class MysqlController {

    @Autowired
    private IUserService userService;

    @RequestMapping("getUser/{id}")
    public String GetUser(@PathVariable int id){

        return userService.Sel(id);
    }

    @RequestMapping(value = "getUser/", method = RequestMethod.POST)
    public String GetUser(@RequestParam("name") String name){

        return userService.Sel(name);
    }

}
