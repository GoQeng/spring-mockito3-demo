package com.example.mockito.demo.service;

import com.example.mockito.demo.domain.Demo;
import com.example.mockito.demo.dto.MobileInfoDTO;
import com.example.mockito.demo.mapper.DemoMapper;
import com.example.mockito.demo.util.AESUtil;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    private static final String MOBILE_URL = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";

    @Resource
    private DemoMapper demoMapper;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void addDemo(Demo demo) {
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong("testMockito");
        long count = rAtomicLong.get();
        if (count > 3) {
            demoMapper.insert(demo);
        } else {
            logger.info("the current count is {}", count);
        }
    }

    @Override
    public List<Demo> getList() {
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong("testMockito");
        long count = rAtomicLong.incrementAndGet();
        if (count == 4) {
            Demo demo = new Demo();
            demo.setId(4);
            demo.setName("testCount4");
            List<Demo> list = new ArrayList<>();
            list.add(demo);
            return list;
        }

        String encryptContent = AESUtil.encrypt("test", "1234567890123456");
        if ("demo".equals(encryptContent)) {
            logger.info("prepare to get encrypt content");
            Demo demo = new Demo();
            demo.setId(1);
            demo.setName("testEncrypt");
            List<Demo> list = new ArrayList<>();
            list.add(demo);
            return list;
        }
        List<Demo> list = new ArrayList<>();
        MobileInfoDTO mobileInfoDTO = restTemplate.getForObject(MOBILE_URL, MobileInfoDTO.class);
        if (mobileInfoDTO != null) {
            logger.info("the mobile location info is {}", mobileInfoDTO);
            Demo demo = new Demo();
            demo.setId(3);
            demo.setName(mobileInfoDTO.getName());
            list.add(demo);
        }
        return list;
    }

    @Override
    public Demo getDetail(Integer id) {
        return demoMapper.getDetail(id);
    }
}
