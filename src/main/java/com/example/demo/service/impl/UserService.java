package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserService implements IUserService {

    private static final Logger logger = LogManager.getLogger("UserService");

    @Autowired
    private UserMapper userMapper;

    @Override
    public String Sel(int id){

        logger.info("query id:" + id);

        return userMapper.Sel(id).toString();
    }

    @Override
    public String Sel(String name){

        logger.info("query name:" + name);

        return userMapper.SelbyName(name).toString();
    }

    @Override
    public Map<Object,Object> chartdata1() {

        Map<Object,Object> ret = new HashMap<>();


        List<Map<Object,Object>> maleList = userMapper.queryMale();

        List<Map<Object,Object>> femaleList = userMapper.queryFemale();

        ret.put("male",formatData(maleList,"count"));
        ret.put("female",formatData(femaleList,"count"));
        return ret;
    }

    @Override
    public Map<Object,Object> chartdata2() {
        Map<Object,Object> ret = new HashMap<>();
        List<Map<Object,Object>> maleList = userMapper.queryMale2();
        List<Map<Object,Object>> femaleList = userMapper.queryFemale2();

        ret.put("malemin",formatData(maleList,"mins"));
        ret.put("malemax",formatData(maleList,"maxs"));
        ret.put("maleavg",formatData(maleList,"avgs"));
        ret.put("femalemin",formatData(femaleList,"mins"));
        ret.put("femalemax",formatData(femaleList,"maxs"));
        ret.put("femaleavg",formatData(femaleList,"avgs"));
        return ret;
    }

    @Override
    public Map<Object,Object> chartdata3() {

        return null;
    }

    @Override
    public Map<Object,Object> chartdata4() {

        return null;
    }

    @Override
    public Map<Object,Object> chartdata5() {

        Map<Object,Object> ret = new HashMap<>();

        List<Map<Object,Object>> maleList = userMapper.queryMale5();

        List<Map<Object,Object>> femaleList = userMapper.queryFemale5();

        ret.put("male",formatData(maleList,"count"));
        ret.put("female",formatData(femaleList,"count"));
        return ret;
    }

    private List<Object> formatData(List<Map<Object,Object>> paramMap,String key){
        List<Object> ret = new ArrayList<>();
        for (Map<Object, Object> map : paramMap) {
            ret.add(map.get(key));
        }
        return ret;
    }

}
