package com.example.demo.service;

public interface IHbaseService {

    public abstract void createtable() throws Exception;

    public abstract void putData() throws Exception;

    public abstract void getData() throws Exception;

}
