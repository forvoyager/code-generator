package ${basePackageName}.${moduleName}.service;

import ${basePackageName}.${moduleName}.common.model.${modelName?cap_first}Model;
import com.xr.base.jdbc.service.IBaseService;

/**
 * <b>time</b>: ${time} <br>
 * <b>author</b>: ${author}
 * <b>description</b>: ${comments} 服务定义 <br>
 */
public interface I${modelName?cap_first}Service extends IBaseService<${modelName?cap_first}Model> {
}
