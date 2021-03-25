package com.entdiy.mdm.controller.admin;


import com.entdiy.common.constant.UriPrefix;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.mdm.entity.DictItem;
import com.entdiy.mdm.service.IDictItemService;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典列表数据 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
@Api(tags = "字典列表数据管理")
@RestController
@RequestMapping("/mdm/admin/dict-item")
public class DictItemController extends BaseRestController<IDictItemService, DictItem> {

    @Getter
    @Autowired
    private IDictItemService baseService;

}
