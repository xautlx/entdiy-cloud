package com.entdiy.common.web;

import com.entdiy.common.constant.BaseConstant;
import com.entdiy.common.exception.Validation;
import com.entdiy.common.mapper.SysUtilMapper;
import com.entdiy.common.model.DuplicateCheckVo;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.service.ISupportService;
import com.entdiy.common.util.StringUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Api(tags = "辅助功能")
@RestController
public class SysUtilController {

    @Autowired
    private ISupportService iSupportService;

    @Autowired
    private SysUtilMapper sysUtilMapper;

    @Autowired
    private AppConfigProperties appConfigProperties;

    @PreAuthorize(BaseConstant.PreAuthorizePermitAll)
    @GetMapping("/util/ping")
    public String ping() {
        return "Pong at " + LocalDateTime.now();
    }

    @PreAuthorize(BaseConstant.PreAuthorizePermitAll)
    @GetMapping("/pub/app-info")
    public ViewResult<Map<String, Object>> appInfo() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("buildVersion", appConfigProperties.getBuildVersion());
        data.put("systemName", appConfigProperties.getSystemName());
        if (appConfigProperties.isDevMode()) {
            data.put("devMode", appConfigProperties.isDevMode());
        }
        return ViewResult.success(data);
    }

    /**
     * 校验数据是否在系统中是否存在
     *
     * @return
     */
    @PostMapping(value = "/util/duplicate/check")
    @ApiOperation("重复校验接口")
    public ViewResult<Object> doDuplicateCheck(@RequestBody DuplicateCheckVo duplicateCheckVo) {
        log.debug("Duplicate check: {}", duplicateCheckVo);
        Map<String, Object> fieldPairs = duplicateCheckVo.getFieldPairs();
        Validation.isTrue(fieldPairs != null && fieldPairs.size() > 0, "约束字段不能为空");
        //由于SQL Map中直接${}语法输出字段名称，因此需要做SQL字段名称SQL注入校验防止恶意攻击
        fieldPairs.keySet().forEach(fieldName -> StringUtil.assertSqlInjection(fieldName));
        Long num = sysUtilMapper.duplicateCheckCountSql(duplicateCheckVo);

        if (num == null || num == 0) {
            return ViewResult.success();
        } else {
            return ViewResult.error("该值不可用，系统中已存在").skipMessage(true);
        }
    }

    @ApiOperation(value = "模型元数据")
    @GetMapping("/util/meta-data/rebuild-meta-class")
    public ViewResult<Map<String, Map<String, Object>>> modelMetaData(@RequestParam(value = "metaClasses", required = true) String metaClasses) {
        Map<String, Object> metaData = Maps.newHashMap();
        Arrays.stream(metaClasses.split(",")).forEach(metaClass -> {
            metaClass = metaClass.trim();
            metaData.put(metaClass, iSupportService.buildModelMetaDataCacheable(metaClass).getMeteData());
        });
        return ViewResult.success(metaData)
                .version(ApplicationContextHolder.getBuildVersion());
    }
}
