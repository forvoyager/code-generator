package ${basePackageName}.${moduleName}.common.model;

import com.xr.base.core.model.BaseModel;

/**
 * <b>author</b>: ${author}
 * <b>time</b>: ${time} <br>
 * <b>description</b>: ${comments} 模型 <br>
 */
public class ${modelName?cap_first}Model extends BaseModel {

  <#list fieldList as field>
  public static final String ${field.name?upper_case} = "${field.name}";
  </#list>

  <#list fieldList as field>
  /**
   * ${field.comment}
   */
  private ${field.javaTypeName} ${field.name};
  </#list>

  <#list fieldList as field>
  public ${field.javaTypeName} get${field.name?cap_first}() {
    return this.${field.name};
  }
  public ${modelName?cap_first}Model set${field.name?cap_first}(${field.javaTypeName} ${field.name}) {
    this.${field.name} = ${field.name};
    return this;
  }

  </#list>
}

