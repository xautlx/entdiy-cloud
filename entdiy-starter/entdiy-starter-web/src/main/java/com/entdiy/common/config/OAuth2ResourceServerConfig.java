package com.entdiy.common.config;

import com.entdiy.auth.security.RevokableJwtTokenStore;
import com.entdiy.common.filter.AuthDataHolderFilter;
import com.entdiy.common.logger.web.HttpRequestLogServletFilter;
import com.entdiy.common.security.TenantAccessTokenConverter;
import com.entdiy.common.web.AppConfigProperties;
import com.entdiy.common.web.GlobalWebExceptionResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.header.HeaderWriterFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableResourceServer
@EnableConfigurationProperties({AppConfigProperties.class})
@Slf4j
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AppConfigProperties appConfigProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(new TenantAccessTokenConverter());
        converter.setSigningKey(appConfigProperties.getSecurity().getJwtSigningKey());
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new RevokableJwtTokenStore(accessTokenConverter());
    }

    public class DelegateBearerTokenExtractor extends BearerTokenExtractor {

        @Override
        @SneakyThrows
        protected String extractHeaderToken(HttpServletRequest request) {
            String token = super.extractHeaderToken(request);
            //如果从请求未获取到token则尝试去开发模拟token，方便开发调试
            if (appConfigProperties.isDevMode() && StringUtils.isEmpty(token)) {
                token = "mockClientJwtToken(clientCredentialsResourceDetails.getClientId())";
            }
            return token;
        }
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setTokenEnhancer(accessTokenConverter());
        return tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore())
                .tokenExtractor(new DelegateBearerTokenExtractor())
                //定制把所有认证授权异常转换为封装结构响应
                .authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .expressionHandler(new OAuth2WebSecurityExpressionHandler());
    }

    public class CustomAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(GlobalWebExceptionResolver.buildResponseBody(request,response, e)));
        }
    }

    public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(GlobalWebExceptionResolver.buildResponseBody(request,response, e)));
        }
    }



    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();

        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

//        if (log.isInfoEnabled()) {
//            log.info("Using securityProperties: {}", appConfigProperties.getSecurity());
//        }

        //开放权限请求
        String[] permitAllUrls = new String[]{
                "/",
                "/login",
                "/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/index.html",
                "/webjars/**",
                "/druid/**",
                "/csrf",
                "/favicon.ico",
                "/error",
                "/pub/**"};
//        if (ArrayUtils.isNotEmpty(securityProperties.getIgnoreUrls())) {
//            permitAllUrls = ArrayUtils.addAll(permitAllUrls, securityProperties.getIgnoreUrls());
//        }

        if (log.isInfoEnabled()) {
            log.info("Using permitAll Url list: {}", StringUtils.join(permitAllUrls, ","));
        }
        http.authorizeRequests().antMatchers(permitAllUrls).permitAll();

        /**
         * @see SecurityExpressionRoot
         * @see OAuth2SecurityExpressionMethods
         */
//        if (securityProperties.getAclMappings() != null && securityProperties.getAclMappings().size() > 0) {
//            Map<String, String> sortedMap = new LinkedHashMap<>();
//            securityProperties.getAclMappings().entrySet().stream()
//                    .sorted(Map.Entry.comparingByKey())
//                    .forEachOrdered(one -> sortedMap.put(one.getKey(), one.getValue()));
//            for (Map.Entry<String, String> me : sortedMap.entrySet()) {
//                http.authorizeRequests().antMatchers(StringUtils.substringAfter(me.getKey(), ":").trim()).access(me.getValue());
//            }
//        }
        http.authorizeRequests().anyRequest().authenticated();

        //添加请求日志打印过滤器
        http.addFilterBefore(new HttpRequestLogServletFilter(), HeaderWriterFilter.class);

        //建议采用Nginx层面做CORS处理
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);
//        //插入HttpRequestLogServletFilter之前，OPTIONS请求则不再继续处理日志记录
//        http.addFilterBefore(corsFilter, HttpRequestLogServletFilter.class);


        //搬迁JWT认证信息到TheadLocal容器对象
        http.addFilterAfter(new AuthDataHolderFilter(), AbstractPreAuthenticatedProcessingFilter.class);

//        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientFilter=new OAuth2ClientAuthenticationProcessingFilter("/index.html");
//        oAuth2ClientFilter.setAuthenticationSuccessHandler(new ForwardAuthenticationSuccessHandler("/index.html"));
//        http.addFilterAfter(oAuth2ClientFilter, AbstractPreAuthenticatedProcessingFilter.class);

        //添加 自定义 filter
//        UrlFilterSecurityInterceptor urlFilterSecurityInterceptor = new UrlFilterSecurityInterceptor();
//        List<AccessDecisionVoter<? extends Object>> decisionVoters = Lists.newArrayList();
//        decisionVoters.add(new WebExpressionVoter());
//        decisionVoters.add(new RoleVoter());
//        AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);
//        urlFilterSecurityInterceptor.setAccessDecisionManager(affirmativeBased);
//        urlFilterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource());
//        //http.addFilterAfter(urlFilterSecurityInterceptor, FilterSecurityInterceptor.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
