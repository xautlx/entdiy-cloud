package com.entdiy.mdm.controller.admin;

import com.entdiy.common.constant.BaseConstant;
import com.entdiy.common.constant.UriPrefix;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.web.ApplicationContextHolder;
import com.entdiy.common.web.anna.MenuData;
import com.entdiy.mdm.entity.Menu;
import com.entdiy.mdm.entity.Permission;
import com.entdiy.mdm.service.IMenuService;
import com.entdiy.mdm.service.IPermissionService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "主数据辅助功能")
public class SupportController {

    @Autowired
    private IMenuService iMenuService;

    @Autowired
    private IPermissionService iPermissionService;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @ApiOperation(value = "基于注解更新菜单权限等数据")
    @PreAuthorize(BaseConstant.PreAuthorizePermitAll)
    @PostMapping("/meta-data/rebuild-annotation")
    public ViewResult<Map<String, Object>> rebuild() {
        if (!ApplicationContextHolder.isDevMode()) {
            return ViewResult.error("元数据重建操作仅在开发模式下可用");
        }
        Map<String, Object> data = Maps.newHashMap();
        List<Menu> menus = iMenuService.findAllCacheable();
        List<Permission> permissions = iPermissionService.findAllCacheable();

        log.info("Rebuilding menu and permission data ...");
        //合并所有类中所有Controller URL定义信息
        Set<String> mergedPermissions = Sets.newLinkedHashSet();
        //合并所有类中所有菜单注解定义信息
        Set<String> mergedMenus = Sets.newHashSet();
        //缓存记录所有菜单注解定义，用于检查提示重复注解定义
        Map<String, MenuData> menuDataMap = Maps.newHashMap();

        Set<Class<?>> controllerClassSet = Sets.newHashSet();
        requestMappingHandlerMapping.getHandlerMethods().forEach((info, handlerMethod) -> {
            controllerClassSet.add(handlerMethod.getBeanType());
        });

        controllerClassSet.forEach(controllerClass -> {
            //菜单数据处理
            MenuData menuData = controllerClass.getAnnotation(MenuData.class);
            if (menuData != null) {
                if (menuDataMap.get(menuData.component()) != null) {
                    log.warn("Skipped as component value undefined", menuData.value());
                    return;
                }
                if (menuDataMap.get(menuData.value()) != null) {
                    log.warn("重复菜单数据注解: {}, 请检查", menuData.value());
                }
                menuDataMap.put(menuData.value(), menuData);

                String menuPath = menuData.value();
                String[] pathNames = menuPath.split("/");
                List<String> names = Arrays.stream(pathNames).filter(one -> StringUtils.isNotBlank(one)).collect(Collectors.toList());
                int pathLevel = names.size();
                for (int i = 0; i < pathLevel; i++) {
                    String path = "/" + StringUtils.join(names, "/", 0, i + 1);
                    if (!mergedMenus.contains(path)) {
                        //记录已处理path，下次循环跳过
                        mergedMenus.add(path);
                        //取数据库记录，如果没有则创建
                        Menu item = menus.stream().filter(one -> path.equals(one.getNamePath())).findFirst().orElse(null);
                        if (item == null) {
                            item = new Menu();
                            menus.add(item);
                        }
                        item.setNamePath(path);
                        String parentPath = "/" + StringUtils.join(names, "/", 0, i);
                        Menu parent = menus.stream().filter(one -> parentPath.equals(one.getNamePath())).findFirst().orElse(null);
                        if (parent != null) {
                            item.setParentId(parent.getId());
                            if (parent.getLeafNode()) {
                                parent.setLeafNode(Boolean.FALSE);
                                iMenuService.saveOrUpdate(parent);
                            }
                        }
                        item.setName(names.get(i));
                        item.setUrl(menuData.entryUri());
                        //计算菜单对应URL路径
                        if (i + 1 == pathLevel) {
                            item.setComponent(menuData.component());
                        }
                        if (StringUtils.isNotBlank(menuData.comments())) {
                            item.setRemark(menuData.comments());
                        }

                        iMenuService.saveOrUpdate(item);
                    }
                }
            }
        });

        requestMappingHandlerMapping.getHandlerMethods().forEach((info, handlerMethod) -> {
            Method method = handlerMethod.getMethod();
            Class<?> controllerClass = handlerMethod.getBeanType();

            String requestUri = StringUtils.join(info.getPatternsCondition().getPatterns(), ",");
            String requestMethod = StringUtils.join(info.getMethodsCondition().getMethods(), ",");
            if (!StringUtils.contains(requestUri, UriPrefix.ADMIN)) {
                return;
            }

            PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
            if (preAuthorize != null && preAuthorize.value().equals(BaseConstant.PreAuthorizePermitAll)) {
                return;
            }

            StringBuilder pathName = new StringBuilder();
            Api classApi = controllerClass.getAnnotation(Api.class);
            if (classApi != null) {
                if (classApi.hidden()) {
                    return;
                }
                if (StringUtils.isNotBlank(classApi.value())) {
                    pathName.append("/" + classApi.value());
                }
                if (ArrayUtils.isNotEmpty(classApi.tags()) && StringUtils.isNotEmpty(classApi.tags()[0])) {
                    pathName.append("/" + StringUtils.join(classApi.tags(), ","));
                }
            }
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                if (apiOperation.hidden()) {
                    return;
                }
                pathName.append("/" + apiOperation.value());

                Permission item = permissions.stream().filter(one -> requestUri.equals(one.getCode())).findFirst().orElse(null);
                if (item == null) {
                    item = new Permission();
                    item.setCode(requestUri);
                    permissions.add(item);
                }
                String name = pathName.toString();
                item.setName(name);
                item.setRequestMethod(requestMethod);
                item.setRequestUri(requestUri);

                iPermissionService.saveOrUpdate(item);
            }
        });

        data.put("menus", menus);
        data.put("permissions", permissions);
        return ViewResult.success(data);
    }


}
