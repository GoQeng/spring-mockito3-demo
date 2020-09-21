package com.example.mockito.demo.controller;

import com.example.mockito.demo.base.SpringBaseTest;
import com.example.mockito.demo.service.DemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;

public class DemoControllerTest extends SpringBaseTest {

    private MockMvc mockMvc;

    @Mock
    private DemoService demoService;

    //把demoService注入到demoController中
    @InjectMocks
    private DemoController demoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        //手动构建，不使用@AutoConfigureMockMvc注解避免重复启动spring上下文
        this.mockMvc = MockMvcBuilders.standaloneSetup(demoController).build();
    }

    @Test
    public void addDemoTest() throws Exception {
        String name = "mock demo add";
        mockMvc.perform(MockMvcRequestBuilders.post("/demo")
                .param("name", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("OK")))
                .andDo(MockMvcResultHandlers.print());
        //验证调用次数，发生过一次
        Mockito.verify(demoService, Mockito.times(1)).addDemo(ArgumentMatchers.any());
    }

    @Test
    public void getDemoListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/demos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"))
                .andDo(MockMvcResultHandlers.print());
        //验证调用次数，发生过一次
        Mockito.verify(demoService, Mockito.times(1)).getList();
    }

    @Test
    public void getDemoDetailTest() throws Exception {
        String name = "mock demo getDetail";
        mockMvc.perform(MockMvcRequestBuilders.get("/demo/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("")))
                .andDo(MockMvcResultHandlers.print());
        //验证调用次数，发生过一次
        Mockito.verify(demoService, Mockito.times(1)).getDetail(ArgumentMatchers.anyInt());
    }
}
