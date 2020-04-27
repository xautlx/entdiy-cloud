package ${package.Dto}.mapper;

import ${package.Entity}.${entity};
import ${package.Dto}.${entity}ListDto;
import ${superModelMapperClassPackage};
import org.mapstruct.Mapper;

/**
* <p>
 * ${table.comment!} ListDtoMapper
 * </p>
*
* @author ${author}
* @since ${date}
*/
@Mapper
public interface ${entity}ListDtoMapper extends ${superModelMapperClass}<${entity}, ${entity}ListDto> {

}
