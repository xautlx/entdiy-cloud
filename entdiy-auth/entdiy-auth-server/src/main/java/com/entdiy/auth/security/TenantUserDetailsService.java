package com.entdiy.auth.security;

import com.entdiy.auth.constant.AuthConstant;
import com.entdiy.auth.model.TenantAuthModel;
import com.entdiy.auth.model.TenantUserDetails;
import com.entdiy.auth.model.UserAuthModel;
import com.entdiy.auth.service.IAuthService;
import com.entdiy.common.constant.BaseConstant;
import com.entdiy.common.exception.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class TenantUserDetailsService implements UserDetailsService {

    @Resource
    private TokenStore tokenStore;

    @Autowired
    private IAuthService iAuthService;

    @Override
    public TenantUserDetails loadUserByUsername(String compositeName)
            throws UsernameNotFoundException {
        //将组合的登录账号数据进行属性拆分
        String[] splits = compositeName.split(AuthConstant.USERNAME_DATA_SPLIT);
        String accountName = Validation.notBlank(splits[0], "登录账号不能为空");
        String tenantCode = BaseConstant.ROOT;
        if (splits.length > 1) {
            tenantCode = splits[1];
        }

        TenantAuthModel tenantAuth = iAuthService.findTenantDetail(tenantCode);
        Validation.notNull(tenantAuth, "租户代码无效: {}", tenantCode);

        tenantAuth.setTenantCode(tenantCode);
        LocalDate today = LocalDate.now();
        boolean tenantAccountNonExpired = true;
        if (tenantAuth.getValidStartDate() != null && tenantAuth.getValidStartDate().isAfter(today)) {
            tenantAccountNonExpired = false;
        }
        if (tenantAuth.getValidEndDate() != null && tenantAuth.getValidEndDate().isBefore(today)) {
            tenantAccountNonExpired = false;
        }
        Validation.isTrue(tenantAccountNonExpired, "租户尚未启用或已过期: {}", tenantCode);

        UserAuthModel userAuth = iAuthService.findUserDetail(tenantAuth.getId(), accountName);
        Validation.notNull(userAuth, "登录账号无效: {}", accountName);

        boolean userAccountNonExpired = true;
        if (userAuth.getValidStartDate() != null && userAuth.getValidStartDate().isAfter(today)) {
            userAccountNonExpired = false;
        }
        if (userAuth.getValidEndDate() != null && userAuth.getValidEndDate().isBefore(today)) {
            userAccountNonExpired = false;
        }
        Validation.isTrue(userAccountNonExpired, "账号尚未启用或已过期: {}", accountName);
        Validation.isTrue(userAuth.getAccountNonLocked(), "账号已锁定: {}", accountName);

        //重复登录校验
//            String enableSingleLoginCheckDictionary = iDictionaryService.loadDictionaryValue(authData.getCompanyId(),
//                    "security", "is_enable_single_login_check");
//            if ("1".equals(enableSingleLoginCheckDictionary) || BooleanUtils.toBoolean(enableSingleLoginCheckDictionary)) {
//                String systemType = authData.getSystemType();
//                Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(authData.getClientId(), compositeName);
//                if (!CollectionUtils.isEmpty(tokens)) {
//                    for (OAuth2AccessToken token : tokens) {
//                        OAuth2Authentication authentication = tokenStore.readAuthentication(token);
//                        if (authentication != null) {
//                            Object principal = authentication.getPrincipal();
//                            if (principal instanceof DomainUserDetails) {
//                                DomainUserDetails domainUserDetails = (DomainUserDetails) principal;
//                                //找到之前通类型登录token，移除
//                                if (domainUserDetails.getSystemType() != null && domainUserDetails.getSystemType().equals(systemType)) {
//                                    tokenStore.removeRefreshToken(token.getRefreshToken());
//                                    tokenStore.removeAccessToken(token);
//                                }
//                            }
//                        }
//                    }
//                }
//            }


        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        List<String> roles = iAuthService.findUserRoles(userAuth.getId());
        if (roles != null) {
            for (String single : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(single));
            }
        }

        if (BaseConstant.ROOT.equals(tenantCode)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(BaseConstant.ROLE_ROOT_TENANT));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority(BaseConstant.ROLE_USER_TENANT));
        }

        TenantUserDetails userDetails = new TenantUserDetails(compositeName, userAuth.getPassword(), grantedAuthorities);
        userDetails.setUserId(userAuth.getId());
        userDetails.setTenantCode(tenantCode);
        userDetails.setTenantId(tenantAuth.getId());
        return userDetails;
    }


}
