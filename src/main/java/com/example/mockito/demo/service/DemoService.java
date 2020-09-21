package com.example.mockito.demo.service;

import com.example.mockito.demo.domain.Demo;

import java.util.List;

public interface DemoService {

    void addDemo(Demo demo);

    List<Demo> getList();

    Demo getDetail(Integer id);
}
