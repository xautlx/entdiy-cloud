package com.entdiy.boot.autoconfigure.web;

import com.entdiy.common.constant.CurdConstant;
import com.entdiy.common.web.AppContextHolder;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Profile({"local", "dev", "test"})
public class SwaggerConfig {

    @Bean
    public Docket createOpenApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                //页面标题
                .title(AppContextHolder.getSystemName() + " Restful API")
                //创建人
                .contact(new Contact("Li Xia", "", "xautlx@hotmail.com"))
                //版本号
                .version(AppContextHolder.getBuildVersion())
                //描述
                .description("外部接口")
                .build();

        //添加header参数
        List<Parameter> pars = new ArrayList<>();
        pars.add(buildParameter("Authorization", "Bearer {Access Token}"));

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                //为当前包路径
                .apis(regexBasePackage("com.entdiy"))
                .paths(PathSelectors.ant(CurdConstant.UriPrefix.API.getPrefix() + "/**"))
                .build()
                .globalOperationParameters(pars)
                .groupName("外部接口");
    }

    @Bean
    public Docket createRpcApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                //页面标题
                .title(AppContextHolder.getSystemName() + " RPC API")
                //创建人
                .contact(new Contact("Li Xia", "", "xautlx@hotmail.com"))
                //版本号
                .version(AppContextHolder.getBuildVersion())
                //描述
                .description("内部接口")
                .build();

        //添加header参数
        List<Parameter> pars = new ArrayList<>();
        pars.add(buildParameter("Authorization", "Bearer {Access Token}"));

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                //为当前包路径
                .apis(regexBasePackage("com.entdiy"))
                .paths(PathSelectors.ant(CurdConstant.UriPrefix.RPC.getPrefix() + "/**"))
                .build()
                .globalOperationParameters(pars)
                .groupName("内部接口");
    }

    private Parameter buildParameter(String name, String descn) {
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(name)
                .description(descn)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .order(100)
                .build();
        return tokenPar.build();
    }

    public static Predicate<RequestHandler> regexBasePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(",")) {
                String pkgName = input.getPackage().getName();
                boolean isMatch = pkgName.startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

}