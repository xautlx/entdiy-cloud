package com.entdiy.mdm.service.test;

import com.entdiy.common.test.SpringBootTransactionalTest;
import com.entdiy.mdm.entity.Menu;
import com.entdiy.mdm.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class IMenuServiceTest extends SpringBootTransactionalTest {

    @Autowired
    private IMenuService iMenuService;

    @Before
    public void beforeEach() {
        log.debug("Prepare mock data...");
        iMenuService.saveOrUpdate(buildMockObject(Menu.class));
        iMenuService.saveOrUpdate(buildMockObject(Menu.class));
    }

    @Test
    public void enableServiceDefaultCacheTest() {
        Menu entity = buildMockObject(Menu.class);
        iMenuService.saveOrUpdate(entity);

        log.debug("1 getById...");
        iMenuService.getById(entity.getId());

        log.debug("2 getById...");
        iMenuService.getById(entity.getId());

        log.debug("1 list...");
        iMenuService.list(null);

        log.debug("2 list...");
        iMenuService.list(null);

        log.debug("1 findAllCacheable...");
        iMenuService.findAllCacheable();

        log.debug("2 findAllCacheable...");
        iMenuService.findAllCacheable();

        log.debug("2.5 save...");
        iMenuService.saveOrUpdate(buildMockObject(Menu.class));

        log.debug("3 findAllCacheable...");
        iMenuService.findAllCacheable();
    }
}
