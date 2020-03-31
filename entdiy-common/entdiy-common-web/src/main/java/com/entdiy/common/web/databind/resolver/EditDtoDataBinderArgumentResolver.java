package com.entdiy.common.web.databind.resolver;

import com.entdiy.common.controller.BaseController;
import com.entdiy.common.model.BaseModelMapper;
import com.entdiy.common.service.BaseService;
import com.entdiy.common.web.ApplicationContextHolder;
import com.entdiy.common.web.databind.annotation.EditDtoDataBinder;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 定制对  {@link EditDtoDataBinder}  注解参数处理，基于id参数组从数据库查询对象
 *
 * @see org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor
 */
@Slf4j
public class EditDtoDataBinderArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(EditDtoDataBinder.class);
    }

    private Object findEntityById(BaseController controller, Class<?> entityIdClass, String id) {
        Serializable convertedId;
        if (String.class.equals(entityIdClass)) {
            convertedId = id;
        } else if (Long.class.equals(entityIdClass)) {
            convertedId = Long.valueOf(id);
        } else if (Integer.class.equals(entityIdClass)) {
            convertedId = Integer.valueOf(id);
        } else {
            throw new IllegalStateException("Unsupported entity ID class: " + entityIdClass);
        }

        BaseService baseService = controller.getBaseService();
        Assert.state(baseService != null, "controller baseService required");
        //查询实体对象
        return baseService.getById(convertedId);
    }

    private void dataAccessControl(EditDtoDataBinder ann, Object entity, Class controllerClass) {
//        //优先从方法注解获取权限控制表达式字符串
//        String dataAccessControl = ann.dataAccessControl();
//        //如果方法层面注解值为空，则继续从所属Controller加载注解属性值
//        if (StringUtils.isBlank(dataAccessControl)) {
//            EntityAttribute controllerAnn = (EntityAttribute) controllerClass.getAnnotation(EntityAttribute.class);
//            dataAccessControl = controllerAnn != null ? controllerAnn.dataAccessControl() : null;
//        }
//
//        if (StringUtils.isNotBlank(dataAccessControl)) {
//            EvaluationContext ctx = new StandardEvaluationContext();
//            ctx.setVariable("auth", AuthDataHolder.get());
//            ctx.setVariable("entity", entity);
//            Boolean pass = parser.parseExpression(dataAccessControl).getValue(ctx, Boolean.class);
//            if (!pass) {
//                throw new ServiceException(ApiCommonError.ERROR_NO_ACCESS, ApiCommonError.ERROR_NO_ACCESS_MESSAGE);
//            }
//        }
    }


    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Class controllerClass = parameter.getContainingClass();
        BaseController controller = (BaseController) ApplicationContextHolder.getBean(controllerClass);
        BaseModelMapper baseModelMapper = controller.getEditDtoModelMapper();
        Class<?> entityClass = controller.getEntityClass();

        MethodParameter nestedParameter = parameter.nestedIfOptional();
        Class<?> paramClazz = nestedParameter.getNestedParameterType();
        boolean arrayType = paramClazz.isArray();
        if (arrayType) {
            paramClazz = paramClazz.getComponentType();
        }
        EditDtoDataBinder ann = parameter.getParameterAnnotation(EditDtoDataBinder.class);

        if (arrayType) {
            String[] idParam = webRequest.getParameterValues("id");
            if (ArrayUtils.isEmpty(idParam)) {
                return null;
            }

            Set ids = Sets.newHashSet();
            Stream.of(idParam)
                    .flatMap(idOne -> Arrays.stream(idOne.split(",")))
                    .map(String::trim)
                    .filter(id -> !id.isEmpty())
                    .forEach(id -> ids.add(id));

            if (!CollectionUtils.isEmpty(ids)) {
                //TODO
                //paramClazz.getDeclaredConstructor().newInstance();
            }
            return null;
        } else {
            String id = webRequest.getParameter("id");
            if (StringUtils.isBlank(id)) {
                return paramClazz.getDeclaredConstructor().newInstance();
            } else {
                Class<?> entityIdClass = MethodUtils.getAccessibleMethod(entityClass, "getId").getReturnType();
                //查询实体对象
                Object entity = findEntityById(controller, entityIdClass, id);
                //数据访问权限检查
                dataAccessControl(ann, entity, controllerClass);
                return baseModelMapper.entityToDto(entity);
            }
        }
    }
}
