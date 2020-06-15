package com.xr.code.generate.model;

/**
 * @author Arnold Yand
 * @summary 代码生成需要的各种信息
 * @time 2019/1/31 21:28
 */
public class CodeInfo {

  // 代码类型
  private String codeType;
  // 基础包名
  String basePackageName;
  // 模块名称
  private String moduleName;
  // 作者
  private String author;
  // 时间
  private String time;
  // 代码存放路径
  private String outputPath;
  // 需要去掉的表前缀
  private String skipTablePrefix;
  // 数据库信息
  private DatabaseInfo databaseInfo;
  // 表信息
  private TableInfo tableInfo;

  public String getCodeType() {
    return codeType;
  }

  public CodeInfo setCodeType(String codeType) {
    this.codeType = codeType;
    return this;
  }

  public String getBasePackageName() {
    return basePackageName;
  }

  public CodeInfo setBasePackageName(String basePackageName) {
    this.basePackageName = basePackageName;
    return this;
  }

  public String getModuleName() {
    return moduleName;
  }

  public CodeInfo setModuleName(String moduleName) {
    this.moduleName = moduleName;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public CodeInfo setAuthor(String author) {
    this.author = author;
    return this;
  }

  public String getTime() {
    return time;
  }

  public CodeInfo setTime(String time) {
    this.time = time;
    return this;
  }

  public String getOutputPath() {
    return outputPath;
  }

  public CodeInfo setOutputPath(String outputPath) {
    this.outputPath = outputPath;
    return this;
  }

  public String getSkipTablePrefix() {
    return skipTablePrefix;
  }

  public CodeInfo setSkipTablePrefix(String skipTablePrefix) {
    this.skipTablePrefix = skipTablePrefix;
    return this;
  }

  public DatabaseInfo getDatabaseInfo() {
    return databaseInfo;
  }

  public CodeInfo setDatabaseInfo(DatabaseInfo databaseInfo) {
    this.databaseInfo = databaseInfo;
    return this;
  }

  public TableInfo getTableInfo() {
    return tableInfo;
  }

  public CodeInfo setTableInfo(TableInfo tableInfo) {
    this.tableInfo = tableInfo;
    return this;
  }
}
