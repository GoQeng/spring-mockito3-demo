package com.example.mockito.demo.base;

import com.example.mockito.demo.TestApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = TestApplication.class)
@ExtendWith(SpringExtension.class)
public abstract class SpringBaseTest {
}
