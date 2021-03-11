package com.entdiy.mdm.entity.test;

import com.entdiy.common.test.SpringBootTransactionalTest;
import com.entdiy.common.test.support.MockEntityUtils;
import com.entdiy.mdm.entity.Department;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DepartmentTest extends SpringBootTransactionalTest {

    @Test
    public void save() {
        Department entity = MockEntityUtils.buildMockObject(Department.class);
        entity.save();
    }
}