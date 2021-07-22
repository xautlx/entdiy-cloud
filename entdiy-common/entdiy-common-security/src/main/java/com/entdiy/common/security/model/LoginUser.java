package com.entdiy.common.security.model;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户信息
 *
 */
@Data
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 过期时间(秒数)
     */
    private Long expireIn;

    /**
     * 用户类型: 0=管理端用户，1=C端用户
     */
    private Integer userType;

    /**
     * 用户名id
     */
    private Long userId;


    /**
     * 统一认证用户 id
     */
    private String ssoUserId;


    /**
     * 用户名
     */
    private String username;

    /** 部门ID */
    private Long deptId;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 角色列表
     */
    private List<RoleVO> roleVOs = Lists.newArrayList();

    @Data
    public static class RoleVO {
        private Long roleId;
        private String dataScope;
    }

    public boolean isAdmin() {
        return userId != null && 1L == userId;
    }
}
