package com.example.demo.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    public abstract Map<Object,Object> Sel(int id);

    public abstract Map<Object,Object> SelbyName(String name);

    public abstract List<Map<Object,Object>> queryMale();

    public abstract List<Map<Object,Object>> queryFemale();

    public abstract List<Map<Object,Object>> queryMale2();

    public abstract List<Map<Object,Object>> queryFemale2();

    public abstract List<Map<Object,Object>> queryMale4();

    public abstract List<Map<Object,Object>> queryFemale4();

    public abstract List<Map<Object,Object>> queryMale5();

    public abstract List<Map<Object,Object>> queryFemale5();

}
