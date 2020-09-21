package com.example.mockito.demo.mapper;

import com.example.mockito.demo.base.SpringBaseTest;
import com.example.mockito.demo.domain.Demo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

public class DemoMapperTest extends SpringBaseTest {

    @Resource
    private DemoMapper demoMapper;

    @Test
    public void testInsert() {
        Demo demo = new Demo();
        demo.setName("test");
        demoMapper.insert(demo);

        Integer id = demo.getId();
        Demo model = demoMapper.getDetail(id);
        Assert.assertNotNull(model);
        Assert.assertEquals(demo.getName(), model.getName());
    }

    @Test
    public void testGetList() {
        Demo demo = new Demo();
        demo.setName("test");
        demoMapper.insert(demo);

        List<Demo> demoList = demoMapper.getList();
        Assert.assertNotNull(demoList);
        Assert.assertEquals(1, demoList.size());
    }
}
