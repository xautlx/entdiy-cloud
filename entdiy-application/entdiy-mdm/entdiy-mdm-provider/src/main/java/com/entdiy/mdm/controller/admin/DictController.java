package com.entdiy.mdm.controller.admin;


import com.entdiy.common.constant.UriPrefix;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.common.exception.Validation;
import com.entdiy.common.model.LabelValueBean;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.service.ISupportService;
import com.entdiy.common.web.ApplicationContextHolder;
import com.entdiy.mdm.entity.Dict;
import com.entdiy.mdm.entity.DictItem;
import com.entdiy.mdm.service.IDictItemService;
import com.entdiy.mdm.service.IDictService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典主数据 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
@Api(tags = "字典主数据管理")
@RestController
@RequestMapping("/mdm" + UriPrefix.ADMIN + "/dict")
public class DictController extends BaseRestController<IDictService, Dict> {

    @Getter
    @Autowired
    private IDictService baseService;

    @Autowired
    private IDictItemService iDictItemService;

    @Autowired
    private ISupportService iSupportService;

    @ApiOperation(value = "分类数据列表")
    @GetMapping(value = "/list")
    public ViewResult<Map<String, List<LabelValueBean>>> list(@RequestParam(value = "version", required = false) String version) {
        //如果提供了版本比对参数，则判断是否应用版本变更，但是会带来副作用是导致修改字典数据没法及时反馈到前端
//        if (StringUtils.isNotEmpty(version) && version.equals(ApplicationContextHolder.getBuildVersion())) {
//            return ViewResult.success().version(version);
//        }

        List<Dict> dicts = baseService.findAllCacheable();
        List<DictItem> dictItems = iDictItemService.findAllCacheable();

        Map<String, List<LabelValueBean>> data = Maps.newHashMap();
        dicts.forEach(dict -> {
            String code = dict.getCode();
            data.put(code, dictItems.stream()
                    .filter(one -> one.getEnabled() && one.getDictId().equals(dict.getId()))
                    .collect(Collectors.toList()));
        });

        Map<String, List<LabelValueBean>> enums = iSupportService.buildLabelValueEnumsCacheable();
        enums.keySet().forEach(key -> {
            Validation.isTrue(!data.containsKey(key), "枚举定义与数据字典定义冲突：" + key);
        });
        data.putAll(enums);

        return ViewResult.success(data).version(ApplicationContextHolder.getBuildVersion());
    }

}
