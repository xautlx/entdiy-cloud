package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

import ${package.Dto}.${entity}EditDto;
import ${package.Dto}.mapper.${entity}EditDtoMapper;

import lombok.Getter;
import javax.annotation.Resource;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}, ${entity}EditDto>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}, ${entity}EditDto> implements ${table.serviceName} {

    @Getter
    @Resource
    private ${entity}EditDtoMapper editDtoModelMapper;

}
</#if>
