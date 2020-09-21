package com.example.mockito.demo.mapper;

import com.example.mockito.demo.domain.Demo;

import java.util.List;

public interface DemoMapper {

    void insert(Demo demo);

    List<Demo> getList();

    Demo getDetail(Integer id);
}
