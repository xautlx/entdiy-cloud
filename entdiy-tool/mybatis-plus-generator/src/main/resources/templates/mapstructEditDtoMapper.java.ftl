package ${package.Dto}.mapper;

import ${package.Entity}.${entity};
import ${package.Dto}.${entity}EditDto;
import ${superModelMapperClassPackage};
import org.mapstruct.Mapper;

/**
 * <p>
 * ${table.comment!} EditDtoMapper
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface ${entity}EditDtoMapper extends ${superModelMapperClass}<${entity}, ${entity}EditDto> {

}
