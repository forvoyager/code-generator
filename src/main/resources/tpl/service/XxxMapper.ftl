package ${basePackageName}.${moduleName}.mapper;

import ${basePackageName}.${moduleName}.common.model.${modelName?cap_first}Model;
import com.xr.base.jdbc.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <b>author</b>: ${author}
 * <b>time</b>: ${time} <br>
 * <b>description</b>: ${comments} mapper操作 <br>
 */
@Mapper
public interface ${modelName?cap_first}Mapper extends IBaseMapper<${modelName?cap_first}Model> {
}
