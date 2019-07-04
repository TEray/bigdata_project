package com.example.demo.service;

import java.util.Map;

public interface ISparkService {

    public abstract String wordCount(Map<Object,Object> paramMap) throws Exception;

    public abstract String kafkaSend(Map<Object,Object> paramMap) throws Exception;

    public abstract void analyse4_1();

    public abstract void analyse4_2();

    public abstract void analyse4_3();

    public abstract void analyse4_4();

    public abstract void analyse4_5();


    public abstract void spark2Hbase();


}
