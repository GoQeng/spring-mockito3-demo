package com.example.mockito.demo;

import com.example.mockito.demo.config.RestClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {Application.class, RestClientConfig.class}))
@MapperScan("com.example.mockito.demo.mapper")
public class TestApplication {

}
