package com.example.mockito.demo.controller;

import com.example.mockito.demo.domain.Demo;
import com.example.mockito.demo.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DemoController {

    @Resource
    private DemoService demoService;

    @PostMapping("/demo")
    public String addDemo(Demo demo) {
        demoService.addDemo(demo);
        return "OK";
    }

    @GetMapping("/demos")
    public List<Demo> getDemoList() {
        return demoService.getList();
    }

    @GetMapping("/demo/{id}")
    public Demo getDetail(@PathVariable Integer id) {
        return demoService.getDetail(id);
    }
}
