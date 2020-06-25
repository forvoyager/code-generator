package ${basePackageName}.${moduleName}.service.impl;

import ${basePackageName}.${moduleName}.common.model.${modelName?cap_first}Model;
import ${basePackageName}.${moduleName}.common.service.I${modelName?cap_first}Service;
import ${basePackageName}.${moduleName}.mapper.${modelName?cap_first}Mapper;
import com.xr.base.jdbc.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <b>author</b>: ${author}
 * <b>time</b>: ${time} <br>
 * <b>description</b>: ${comments} 服务实现 <br>
 */
@Service("${modelName}Service")
public class ${modelName?cap_first}ServiceImpl extends BaseServiceImpl<${modelName?cap_first}Mapper, ${modelName?cap_first}Model> implements I${modelName?cap_first}Service {
  @Override
  protected String getPrimaryKeyName() {
    return "${primaryField}";
  }
}
