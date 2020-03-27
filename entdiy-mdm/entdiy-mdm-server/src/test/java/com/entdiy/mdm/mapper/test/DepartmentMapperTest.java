package com.entdiy.mdm.mapper.test;

import com.entdiy.common.test.SpringBootTransactionalTest;
import com.entdiy.mdm.entity.Department;
import com.entdiy.mdm.mapper.DepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("ut")
@Transactional
@Rollback
public class DepartmentMapperTest extends SpringBootTransactionalTest {

    @Resource
    private DepartmentMapper departmentMapper;

    @Test
    public void save() {
        Department entity = new Department();
        departmentMapper.insert(entity);
    }
}