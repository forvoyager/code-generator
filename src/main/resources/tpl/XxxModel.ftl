package com.xr.account.common.model;

import com.xr.base.core.model.BaseModel;

/**
 * <b>time</b>：${time} <br>
 * <b>author</b>：${author}
 * <b>description</b>：${comments} 模型 <br>
 */
public class ${moduleName}Model extends BaseModel {

  <#list fieldList as field>
  public static final String ${field.name?upper_case} = "${field.name}";
  </#list>

  <#list fieldList as field>
  /**
   * ${field.comment}
   */
  private ${field.javaTypeName} ${field.name?lower_case};
  </#list>

  <#list fieldList as field>
  public ${field.javaTypeName} get${field.name}() {
    return this.${field.name?lower_case};
  }
  public ${moduleName}Model set${field.name}(${field.javaTypeName} ${field.name?lower_case}) {
    this.${field.name?lower_case} = ${field.name?lower_case};
    return this;
  }

  </#list>
}

