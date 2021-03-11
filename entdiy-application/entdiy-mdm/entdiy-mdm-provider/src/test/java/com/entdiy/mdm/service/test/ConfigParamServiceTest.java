/**
 * Copyright © 2015 - 2020 EntDIY JavaEE Development Framework
 *
 * Site: https://www.entdiy.com, E-Mail: xautlx@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.entdiy.mdm.service.test;

import com.entdiy.common.test.SpringBootTransactionalTest;
import com.entdiy.common.test.support.MockEntityUtils;
import com.entdiy.mdm.entity.ConfigParam;
import com.entdiy.mdm.service.IConfigParamService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ConfigParamServiceTest extends SpringBootTransactionalTest {

    @Autowired
    private IConfigParamService iConfigParamService;

    @Test
    public void curd() {
        log.debug("Build mock entity...");
        ConfigParam entity = MockEntityUtils.buildMockObject(ConfigParam.class);
        iConfigParamService.saveOrUpdate(entity);
        log.debug("Insert mock entity: {}", entity.getId());
        Assert.assertTrue(entity.getId() != null);

        log.debug("Query validation after insert...");
        ConfigParam item = iConfigParamService.getById(entity.getId());
        Assert.assertTrue(item != null);

        log.debug("Remove entity: {}", entity.getId());
        iConfigParamService.removeByIdWithFill(item);

        log.debug("Query validation after remove...");
        ConfigParam itemAfterDelete = iConfigParamService.getById(entity.getId());
        Assert.assertTrue(itemAfterDelete == null);
    }
}
