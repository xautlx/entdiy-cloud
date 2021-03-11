package com.entdiy.common.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entdiy.common.web.databind.resolver.PageableMethodArgumentResolver;
import com.entdiy.common.web.databind.resolver.QueryWrapperMethodAArgumentResolver;
import com.fasterxml.classmate.TypeResolver;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * 基于官方 springfox-boot-starter 3.0.0 自动化配置
 * 注意：Swagger3访问路径为：/swagger-ui/index.html
 *
 * @link https://springfox.github.io/springfox/docs/current/#releasing-documentation
 */

@Slf4j
@EnableOpenApi
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE-1)
@Profile({"local", "dev", "smoke"})
public class SwaggerConfig {

    @Autowired
    private TypeResolver typeResolver;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${server.port}")
    private int port;

    @PostConstruct
    public void init() {
        if (port > 0) {
            log.info("Swagger3 URL: http://127.0.0.1:{}{}/swagger-ui/index.html", port, contextPath);
        }
    }

    @Bean
    public Docket docketApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(QueryWrapper.class, WildcardType.class),
                                typeResolver.resolve(QueryWrapperMethodAArgumentResolver.QueryWrapperParamStruct.class)),
                        newRule(typeResolver.resolve(Pageable.class),
                                typeResolver.resolve(PageableMethodArgumentResolver.PageableParamStruct.class))
                        //newRule(typeResolver.resolve(ViewResult.class, typeResolver.resolve(Page.class, WildcardType.class)),
                        //      typeResolver.resolve(ViewResult.class, typeResolver.resolve(PageableMethodArgumentResolver.PageResponseStruct.class, WildcardType.class)))
                )
                .enableUrlTemplating(false)
                .globalRequestParameters(authorizationParameter())
                .securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, SecurityScheme.In.HEADER.name())));
    }

    private List authorizationParameter() {
        RequestParameterBuilder tokenBuilder = new RequestParameterBuilder();
        tokenBuilder
                .name("Authorization")
                .description("Bearer [TOKEN]")
                .required(false)
                .in("header")
                .accepts(Collections.singleton(MediaType.APPLICATION_JSON))
                .build();
        return Collections.singletonList(tokenBuilder.build());
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }
}
