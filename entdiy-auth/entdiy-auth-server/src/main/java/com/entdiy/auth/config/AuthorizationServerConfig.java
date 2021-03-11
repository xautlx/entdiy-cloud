package com.entdiy.auth.config;

import com.entdiy.auth.model.TenantUserDetails;
import com.entdiy.auth.security.CustomUserAuthenticationConverter;
import com.entdiy.auth.security.DefaultResourceOwnerPasswordTokenGranter;
import com.entdiy.auth.security.JsonBasicAuthenticationEntryPoint;
import com.entdiy.auth.security.TenantClientDetailsService;
import com.entdiy.auth.security.TenantOAuth2RequestFactory;
import com.entdiy.auth.web.OAuth2ExceptionTranslator;
import com.entdiy.common.constant.BaseConstant;
import com.entdiy.common.logger.web.HttpRequestLogServletFilter;
import com.entdiy.common.web.AppConfigProperties;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@Order(100)
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AppConfigProperties appConfigProperties;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private UserDetailsService userDetailsService;

    @Resource
    private DataSource dataSource;

    /**
     * 注入AuthenticationManager ，密码模式用到
     */
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private TokenStore tokenStore;

    @Bean
    public WebResponseExceptionTranslator exceptionTranslator() {
        return new OAuth2ExceptionTranslator();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //定制处理RefreshToken获取业务属性处理
        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        CustomUserAuthenticationConverter userTokenConverter = new CustomUserAuthenticationConverter();
        userTokenConverter.setUserDetailsService(userDetailsService);
        tokenConverter.setUserTokenConverter(userTokenConverter);
        converter.setAccessTokenConverter(tokenConverter);
        converter.setSigningKey(appConfigProperties.getSecurity().getJwtSigningKey());
        return converter;
    }

    @Bean
    public TenantOAuth2RequestFactory tenantOAuth2RequestFactory() {
        return new TenantOAuth2RequestFactory(tenantClientDetailsService());
    }

    private TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> info = new HashMap();
            Object principal = authentication.getPrincipal();
            if (principal instanceof TenantUserDetails) {
                TenantUserDetails tenantUserDetails = (TenantUserDetails) principal;
                info.put(BaseConstant.AUTH_DATA_USER_ID, tenantUserDetails.getUserId());
                info.put(BaseConstant.AUTH_DATA_TENANT_ID, tenantUserDetails.getTenantId());
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
            return accessToken;
        };
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Lists.newArrayList(tokenEnhancer(), accessTokenConverter()));

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(tenantClientDetailsService());
        tokenServices.setTokenEnhancer(chain);
        return tokenServices;
    }

    private List<TokenGranter> getDefaultTokenGranters() {
        ClientDetailsService clientDetailsService = tenantClientDetailsService();
        AuthorizationServerTokenServices tokenServices = tokenServices();
        OAuth2RequestFactory requestFactory = tenantOAuth2RequestFactory();

        List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
        ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetailsService,
                requestFactory);
        tokenGranters.add(implicit);
        tokenGranters.add(
                new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
        if (authenticationManager != null) {
            tokenGranters.add(new DefaultResourceOwnerPasswordTokenGranter(authenticationManager,
                    tokenServices(), clientDetailsService, requestFactory));
        }
        return tokenGranters;
    }

    private TokenGranter tokenGranter() {
        TokenGranter tokenGranter = new TokenGranter() {
            private CompositeTokenGranter delegate;

            @Override
            public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                if (delegate == null) {
                    delegate = new CompositeTokenGranter(getDefaultTokenGranters());
                }
                return delegate.grant(grantType, tokenRequest);
            }
        };
        return tokenGranter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .exceptionTranslator(exceptionTranslator())
                .authenticationManager(authenticationManager)
                .tokenGranter(tokenGranter())
                .accessTokenConverter(accessTokenConverter())
                .requestFactory(tenantOAuth2RequestFactory())
                .reuseRefreshTokens(true);
    }

    @Bean
    public ClientDetailsService tenantClientDetailsService() {
        return new TenantClientDetailsService(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(tenantClientDetailsService());
    }

    @Bean
    public AuthenticationEntryPoint basicAuthenticationEntryPoint() {
        BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new JsonBasicAuthenticationEntryPoint();
        basicAuthenticationEntryPoint.setRealmName(applicationName);
        return basicAuthenticationEntryPoint;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess(BaseConstant.PreAuthorizePermitAll);
        oauthServer.addTokenEndpointAuthenticationFilter(new HttpRequestLogServletFilter());
        oauthServer.authenticationEntryPoint(basicAuthenticationEntryPoint());
    }

}
