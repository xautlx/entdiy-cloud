package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${package.Dto}.${entity}EditDto;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}, ${entity}EditDto>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}, ${entity}EditDto> {

}
</#if>
