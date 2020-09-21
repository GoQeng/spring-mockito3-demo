package com.example.mockito.demo.service;

import com.example.mockito.demo.base.SpringBaseTest;
import com.example.mockito.demo.domain.Demo;
import com.example.mockito.demo.dto.MobileInfoDTO;
import com.example.mockito.demo.util.AESUtil;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;


public class DemoServiceTest extends SpringBaseTest {

    @Resource
    private DemoService demoService;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RestTemplate restTemplate;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetList() {
        //测试第一个分支逻辑
        RAtomicLong rAtomicLong = Mockito.mock(RAtomicLong.class);
        Mockito.when(redissonClient.getAtomicLong(ArgumentMatchers.anyString())).thenReturn(rAtomicLong);
        long count = 4L;
        Mockito.when(rAtomicLong.incrementAndGet()).thenReturn(count);
        List<Demo> demoList = demoService.getList();
        Assert.assertTrue(demoList != null && demoList.size() == 1);
        Demo demo = demoList.get(0);
        Assert.assertNotNull(demo);
        Assert.assertEquals(Integer.valueOf(4), demo.getId());
        Assert.assertEquals("testCount4", demo.getName());

        //测试第二个分支逻辑
        Mockito.when(redissonClient.getAtomicLong(ArgumentMatchers.anyString())).thenReturn(rAtomicLong);
        count = 1L;
        Mockito.when(rAtomicLong.incrementAndGet()).thenReturn(count);

        MockedStatic<AESUtil> aesUtilMockedStatic = Mockito.mockStatic(AESUtil.class);
        aesUtilMockedStatic.when(() -> AESUtil.encrypt(ArgumentMatchers.eq("test"), ArgumentMatchers.eq("1234567890123456")))
                .thenReturn("demo");

        demoList = demoService.getList();
        Assert.assertTrue(demoList != null && demoList.size() == 1);
        Demo encryptDemo = demoList.get(0);
        Assert.assertNotNull(encryptDemo);
        Assert.assertEquals(Integer.valueOf(1), encryptDemo.getId());
        Assert.assertEquals("testEncrypt", encryptDemo.getName());

        //测试第三个分支逻辑
        Mockito.when(redissonClient.getAtomicLong(ArgumentMatchers.anyString())).thenReturn(rAtomicLong);
        count = 1L;
        Mockito.when(rAtomicLong.incrementAndGet()).thenReturn(count);

        //执行真实方法
        aesUtilMockedStatic.when(() -> AESUtil.encrypt(ArgumentMatchers.eq("test"), ArgumentMatchers.eq("1234567890123456")))
                .thenCallRealMethod();

        String mobileUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";
        MobileInfoDTO mobileInfoDTO = new MobileInfoDTO();
        mobileInfoDTO.setName("testMobile");
        mobileInfoDTO.setLocation("testLocation");
        Mockito.when(restTemplate.getForObject(mobileUrl, MobileInfoDTO.class)).thenReturn(mobileInfoDTO);
        demoList = demoService.getList();
        Assert.assertNotNull(demoList);
        Assert.assertEquals(1, demoList.size());
        Demo demo1 = demoList.get(0);
        Assert.assertNotNull(demo1);
        Assert.assertEquals(mobileInfoDTO.getName(), demo1.getName());
    }
}
