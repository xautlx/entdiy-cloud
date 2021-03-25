package com.entdiy.mdm.controller.admin;


import com.entdiy.common.controller.BaseRestController;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.web.anna.MenuData;
import com.entdiy.mdm.entity.Permission;
import com.entdiy.mdm.service.IPermissionService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-22
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/mdm/admin/permission")
@MenuData(value = "/系统管理/菜单权限",
        component = "/mdm/PermissionList",
        entryUri = "/mdm/admin/permission/query")
public class PermissionController extends BaseRestController<IPermissionService, Permission> {

    @Getter
    @Autowired
    private IPermissionService baseService;

    @ApiOperation(value = "列表数据")
    @GetMapping(value = "/list")
    public ViewResult<List<Permission>> list() {
        return ViewResult.success(baseService.findAllCacheable());
    }

    /**
     * 查询用户拥有的菜单权限和按钮权限
     *
     * @return
     */
    @RequestMapping(value = "/auth-user-permissions", method = RequestMethod.GET)
    public ViewResult<?> authUserPermissions() {

//        //直接获取当前用户不适用前端token
//        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        if (StringUtils.isEmpty(loginUser)) {
//            return Result.error("请登录系统！");
//        }
        List<Permission> metaList = baseService.list();

        Map<String,Object> json = Maps.newHashMap();
        List<Map<String,Object>> menujsonArray = Lists.newArrayList();
        this.getPermissionJsonArray(menujsonArray, metaList, null);
        List<Map<String,Object>> authjsonArray = Lists.newArrayList();
        //this.getAuthJsonArray(authjsonArray, metaList);
        //查询所有的权限
//        LambdaQueryWrapper<Permission> query = new LambdaQueryWrapper<Permission>();
//        query.eq(Permission::getMenuType, CommonConstant.MENU_TYPE_2);
//        //query.eq(Permission::getStatus, "1");
//        List<Permission> allAuthList = sysPermissionService.list(query);
        List<Map<String,Object>> allauthjsonArray = Lists.newArrayList();
       // this.getAllAuthJsonArray(allauthjsonArray, allAuthList);
        //路由菜单
        json.put("menu", menujsonArray);
        //按钮权限（用户拥有的权限集合）
        json.put("auth", authjsonArray);
        //全部权限配置集合（按钮权限，访问权限）
        json.put("allAuth", allauthjsonArray);
        return ViewResult.success(json);

    }

//    private void getTreeList(List<PermissionTree> treeList, List<Permission> metaList, PermissionTree temp) {
//        for (Permission permission : metaList) {
//            String tempPid = permission.getParentId();
//            PermissionTree tree = new PermissionTree(permission);
//            if (temp == null && StringUtils.isEmpty(tempPid)) {
//                treeList.add(tree);
//                if (!tree.getIsLeaf()) {
//                    getTreeList(treeList, metaList, tree);
//                }
//            } else if (temp != null && tempPid != null && tempPid.equals(temp.getId())) {
//                temp.getChildren().add(tree);
//                if (!tree.getIsLeaf()) {
//                    getTreeList(treeList, metaList, tree);
//                }
//            }
//
//        }
//    }
//
//    private void getTreeModelList(List<TreeModel> treeList, List<Permission> metaList, TreeModel temp) {
//        for (Permission permission : metaList) {
//            String tempPid = permission.getParentId();
//            TreeModel tree = new TreeModel(permission);
//            if (temp == null && StringUtils.isEmpty(tempPid)) {
//                treeList.add(tree);
//                if (!tree.getIsLeaf()) {
//                    getTreeModelList(treeList, metaList, tree);
//                }
//            } else if (temp != null && tempPid != null && tempPid.equals(temp.getKey())) {
//                temp.getChildren().add(tree);
//                if (!tree.getIsLeaf()) {
//                    getTreeModelList(treeList, metaList, tree);
//                }
//            }
//
//        }
//    }
//
//    /**
//     * 获取权限JSON数组
//     *
//     * @param jsonArray
//     * @param allList
//     */
//    private void getAllAuthJsonArray(JSONArray jsonArray, List<Permission> allList) {
//        JSONObject json = null;
//        for (Permission permission : allList) {
//            json = new JSONObject();
//            json.put("action", permission.getPerms());
//            json.put("status", permission.getStatus());
//            //1显示2禁用
//            json.put("type", permission.getPermsType());
//            json.put("describe", permission.getName());
//            jsonArray.add(json);
//        }
//    }
//
//    /**
//     * 获取权限JSON数组
//     *
//     * @param jsonArray
//     * @param metaList
//     */
//    private void getAuthJsonArray(List<Map<String,Object>> jsonArray, List<Permission> metaList) {
//        for (Permission permission : metaList) {
//            if (permission.getMenuType() == null) {
//                continue;
//            }
//            if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2) && CommonConstant.STATUS_1.equals(permission.getStatus())) {
//                Map<String,Object> json = Maps.newHashMap();
//                json.put("action", permission.getPerms());
//                json.put("type", permission.getPermsType());
//                json.put("describe", permission.getName());
//                jsonArray.add(json);
//            }
//        }
//    }
//
    /**
     * 获取菜单JSON数组
     *
     * @param jsonArray
     * @param metaList
     * @param parentJson
     */
    private void getPermissionJsonArray(List<Map<String,Object>> jsonArray, List<Permission> metaList, Map<String,Object> parentJson) {
        for (Permission permission : metaList) {
            if (permission.getMenuType() == null) {
                continue;
            }
            Long tempPid = permission.getParentId();
            Map<String,Object> json = getPermissionJsonObject(permission);
            if (json == null) {
                continue;
            }
            if (parentJson == null && tempPid==null) {
                jsonArray.add(json);
                if (!permission.getLeafNode()) {
                    getPermissionJsonArray(jsonArray, metaList, json);
                }
            } else if (parentJson != null && tempPid!=null && tempPid.equals((Long)parentJson.get("id"))) {
                // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                if (permission.getMenuType().equals(Permission.MENU_TYPE_BTN)) {
                    Map<String,Object> metaJson = (Map)parentJson.get("meta");
                    if (metaJson.containsKey("permissionList")) {
                        ((List)metaJson.get("permissionList")).add(json);
                    } else {
                        List<Map<String,Object>> permissionList = Lists.newArrayList();
                        permissionList.add(json);
                        metaJson.put("permissionList", permissionList);
                    }
                    // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                } else if (permission.getMenuType().equals(Permission.MENU_TYPE_SUB) || permission.getMenuType().equals(Permission.MENU_TYPE_ROOT)) {
                    if (parentJson.containsKey("children")) {
                        ((List)parentJson.get("children")).add(json);
                    } else {
                        List<Map<String,Object>> children = Lists.newArrayList();
                        children.add(json);
                        parentJson.put("children", children);
                    }

                    if (!permission.getLeafNode()) {
                        getPermissionJsonArray(jsonArray, metaList, json);
                    }
                }
            }

        }
    }

    /**
     * 根据菜单配置生成路由json
     *
     * @param permission
     * @return
     */
    private Map<String,Object> getPermissionJsonObject(Permission permission) {
        Map<String,Object> json = Maps.newHashMap();
        // 类型(0：一级菜单 1：子菜单 2：按钮)
        if (permission.getMenuType().equals(Permission.MENU_TYPE_BTN)) {
            //json.put("action", permission.getPerms());
            //json.put("type", permission.getPermsType());
            //json.put("describe", permission.getName());
            return null;
        } else if (permission.getMenuType().equals(Permission.MENU_TYPE_ROOT)
                || permission.getMenuType().equals(Permission.MENU_TYPE_SUB)) {
            json.put("id", permission.getId());
            if (permission.getRoute()) {
                json.put("route", "1");// 表示生成路由
            } else {
                json.put("route", "0");// 表示不生成路由
            }

            if (isWWWHttpUrl(permission.getUrl())) {
                json.put("path", Md5Crypt.md5Crypt(permission.getUrl().getBytes()));
            } else {
                json.put("path", permission.getUrl());
            }

            // 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
            if (StringUtils.isNotEmpty(permission.getComponentName())) {
                json.put("name", permission.getComponentName());
            } else {
                json.put("name", urlToRouteName(permission.getUrl()));
            }

            // 是否隐藏路由，默认都是显示的
            if (permission.isHidden()) {
                json.put("hidden", true);
            }
            // 聚合路由
            if (permission.getAlwaysShow()) {
                json.put("alwaysShow", true);
            }
            json.put("component", permission.getComponent());
            Map<String,Object> meta =Maps.newHashMap();
            // 由用户设置是否缓存页面 用布尔值
            if (permission.getKeepAlive()) {
                meta.put("keepAlive", true);
            } else {
                meta.put("keepAlive", false);
            }

            /*update_begin author:wuxianquan date:20190908 for:往菜单信息里添加外链菜单打开方式 */
            //外链菜单打开方式
            if (permission.getInternalOrExternal()) {
                meta.put("internalOrExternal", true);
            } else {
                meta.put("internalOrExternal", false);
            }
            /* update_end author:wuxianquan date:20190908 for: 往菜单信息里添加外链菜单打开方式*/

            meta.put("title", permission.getName());

            //update-begin--Author:scott  Date:20201015 for：路由缓存问题，关闭了tab页时再打开就不刷新 #842
            String component = permission.getComponent();
            if (StringUtils.isNotEmpty(permission.getComponentName()) || StringUtils.isNotEmpty(component)) {
                meta.put("componentName", StringUtils.isNotBlank(permission.getComponentName())?
                        permission.getComponentName():
                        component.substring(component.lastIndexOf("/") + 1));
            }
            //update-end--Author:scott  Date:20201015 for：路由缓存问题，关闭了tab页时再打开就不刷新 #842

            if (permission.getParentId()==null) {
                // 一级菜单跳转地址
                json.put("redirect", permission.getRedirect());
                if (StringUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            } else {
                if (StringUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            }
            if (isWWWHttpUrl(permission.getUrl())) {
                meta.put("url", permission.getUrl());
            }
            json.put("meta", meta);
        }

        return json;
    }

    /**
     * 判断是否外网URL 例如： http://localhost:8080/jeecg-boot/swagger-ui.html#/ 支持特殊格式： {{
     * window._CONFIG['domianURL'] }}/druid/ {{ JS代码片段 }}，前台解析会自动执行JS代码片段
     *
     * @return
     */
    private boolean isWWWHttpUrl(String url) {
        if (url != null && (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("{{"))) {
            return true;
        }
        return false;
    }

    /**
     * 通过URL生成路由name（去掉URL前缀斜杠，替换内容中的斜杠‘/’为-） 举例： URL = /isystem/role RouteName =
     * isystem-role
     *
     * @return
     */
    private String urlToRouteName(String url) {
        if (StringUtils.isNotEmpty(url)) {
            if (url.startsWith("/")) {
                url = url.substring(1);
            }
            url = url.replace("/", "-");

            // 特殊标记
            url = url.replace(":", "@");
            return url;
        } else {
            return null;
        }
    }
}
