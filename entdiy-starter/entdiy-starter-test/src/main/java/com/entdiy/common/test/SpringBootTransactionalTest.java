package com.entdiy.common.test;


import com.entdiy.common.test.support.MockEntityUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 默认已使用H2数据库，因此建议不要在单元测试使用 @Transactional 注解，否则会导致缓存运行异常等情况。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("ut")
@Rollback
public class SpringBootTransactionalTest {

    protected static <X> X buildMockObject(Class<X> clazz) {
        return MockEntityUtils.buildMockObject(clazz);
    }
}
