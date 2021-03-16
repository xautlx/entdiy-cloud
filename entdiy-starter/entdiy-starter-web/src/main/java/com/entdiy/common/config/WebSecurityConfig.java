package com.entdiy.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * SSO处理过程说明：
 * <ul>
 *     <li>前端检测到未登录访问，则浏览器地址转向：/login?to=http://xxx 其中to参数为登录成功处理完成后自动转向返回的前端的绝对URL地址</li>
 *     <li>构造基于OAuth2的authorize_code认证模式转向中央认证服务用户登录界面</li>
 *     <li>认证通过后转回/login?to=http://xxx&code=AYebfQ 根据code自动完成校验换取JWT Token</li>
 *     <li>Token输出到页面JS代码并转向to参数前端页面定制</li>
 * </ul>
 * </p>
 *
 * @see OAuth2ClientAuthenticationProcessingFilter
 */
@Order(1)
@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultTokenServices tokenServices;

    private OAuth2ClientAuthenticationProcessingFilter oauth2SsoFilter() {
        ApplicationContext applicationContext = this.getApplicationContext();
        OAuth2SsoProperties sso = applicationContext.getBean(OAuth2SsoProperties.class);
        OAuth2RestOperations restTemplate = applicationContext.getBean(UserInfoRestTemplateFactory.class).getUserInfoRestTemplate();
        ResourceServerTokenServices tokenServices = applicationContext.getBean(ResourceServerTokenServices.class);
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(sso.getLoginPath());
        filter.setRestTemplate(restTemplate);
        filter.setTokenServices(tokenServices);
        filter.setApplicationEventPublisher(applicationContext);

        //默认成功返回采用Session机制的Redirect方式，定制调整为直接把Token写入前端处理
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                String token = (String) request.getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE);
                String to = request.getParameter("to");
                if (StringUtils.isBlank(to)) {
                    to = "index.html";
                }
                String html = "<script>" +
                        "localStorage.setItem('TOKEN','" + token + "');" +
                        "window.location.href='" + to + "';" +
                        "</script>";
                out.write(html.getBytes());
                out.close();
            }
        });
        return filter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .and()
                .addFilterBefore(oauth2SsoFilter(), BasicAuthenticationFilter.class);

    }
}
