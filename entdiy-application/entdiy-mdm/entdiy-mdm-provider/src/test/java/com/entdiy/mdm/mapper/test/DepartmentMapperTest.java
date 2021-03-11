package com.entdiy.mdm.mapper.test;

import com.entdiy.common.test.SpringBootTransactionalTest;
import com.entdiy.common.test.support.MockEntityUtils;
import com.entdiy.mdm.entity.Department;
import com.entdiy.mdm.mapper.DepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

@Slf4j
public class DepartmentMapperTest extends SpringBootTransactionalTest {

    @Resource
    private DepartmentMapper departmentMapper;

    @Test
    public void save() {
        Department entity = MockEntityUtils.buildMockObject(Department.class);
        departmentMapper.insert(entity);

        entity.setCode("updated");
        departmentMapper.updateById(entity);

        departmentMapper.deleteByIdWithFill(entity);

        Department entity2 = MockEntityUtils.buildMockObject(Department.class);
        departmentMapper.insert(entity2);

        departmentMapper.deleteById(entity2.getId());
    }
}