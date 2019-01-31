package com.xr.code.generate.model;

/**
 * @author Arnold Yand
 * @summary 代码生成需要的各种信息
 * @time 2019/1/31 21:28
 */
public class CodeInfo {

  // 项目名称
  private String projectName;
  // 模块名称
  private String moduleName;
  // 作者
  private String author;
  // 描述信息
  private String comment;
  // 时间
  private String dateTime;
  // 代码存放路径
  private String outputPath;

  // 需要去掉的前缀
  private String prefix;

  // 数据库信息
  private DatabaseInfo databaseInfo;

  // 表信息
  private TableInfo tableInfo;


}
