package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainsiteErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 401){
            return "error";
        }else if(statusCode == 404){
            return "error";
        }else if(statusCode == 403){
            return "error";
        }else{
            return "error";
        }

    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
