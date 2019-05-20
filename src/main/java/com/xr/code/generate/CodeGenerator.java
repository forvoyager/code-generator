package com.xr.code.generate;

import com.xr.code.generate.model.CodeInfo;
import com.xr.code.generate.model.ColumnInfo;
import com.xr.code.generate.model.DatabaseInfo;
import com.xr.code.generate.model.FileData;
import com.xr.code.generate.model.TableInfo;
import com.xr.code.generate.util.FreemarkerUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 代码生成器
 * Created by forvoyager@outlook.com on 2019-01-31 17:21.
 */
public class CodeGenerator {
  public static void main(String[] args) throws Exception {
    // 项目名称
    String projectName = "micro_service";
    // 基础包名
    String basePackageName = "com.xr";
    // 模块名称
    String moduleName = "account";
    // 模块名前缀
    String modulePrefix = "ms-";
    // 作者
    String author = "forvoyager@outlook.com";
    // 代码存放路径
    String outputPath = "./code";

    // 数据库配置
    String url = "jdbc:mysql://localhost:3306/ms_account_db?characterEncoding=UTF-8";
    String driver = "com.mysql.jdbc.Driver";
    String username = "root";
    String password = "123456";
    // 需要去掉的表前缀
    String skipTablePrefix = "ms_";

    // 需要生成代码的表Map<tableName, comment>
    List<String> tables = new ArrayList<String>();
    tables.add("ms_account");

    // 构建生成代码的数据
    new CodeGenerator()
            .setCodeInfo(
                    new CodeInfo()
                            .setProjectName(projectName)
                            .setBasePackageName(basePackageName)
                            .setModuleName(moduleName)
                            .setModulePrefix(modulePrefix)
                            .setAuthor(author)
                            .setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                            .setSkipTablePrefix(skipTablePrefix)
                            .setOutputPath(outputPath)
            )
            .setDatabaseInfo(
                    new DatabaseInfo()
                            .setUrl(url)
                            .setDriver(driver)
                            .setUsername(username)
                            .setPassword(password)
            )
            // base model中存在，其他model中不需要生成
            .addSkipField("create_time")
            .addSkipField("update_time")
            .addSkipField("version")
            .tableInfo(tables)
            .generate();

  }

  /**
   * 生成代码
   */
  private void generate() throws Exception {

    String basePackageName = this.codeInfo.getBasePackageName();
    String moduleName = this.codeInfo.getModuleName();
    String modulePrefix = this.codeInfo.getModulePrefix();

    Map data = new HashMap();
    data.put("author", this.codeInfo.getAuthor());
    data.put("time", this.codeInfo.getTime());
    data.put("basePackageName", basePackageName);
    data.put("moduleName", moduleName);

    List<FileData> files = new ArrayList<FileData>();

    // 生成代码信息
    String code = null;
    FileData file = null;
    for (TableInfo table : tableInfos) {
      data.put("comments", table.getComments());
      data.put("modelName", table.getName());

      // 生成Model
      data.put("fieldList", removeSkipField(table.getColumnList()));
      code = FreemarkerUtils.getFtlToString("/common/XxxModel", data);
      file = new FileData();
      file.setName(upperFirst(table.getName()) + "Model.java");
      file.setContent(code);
      file.setPath(String.format(XxxMODEL_PATH, modulePrefix, moduleName, basePackageName, moduleName));
      files.add(file);

      // 生成IController
      data.put("primaryField", table.getPrimaryColumn());
      data.put("primaryFieldType", table.getPrimaryType());
      code = FreemarkerUtils.getFtlToString("/common/IXxxController", data);
      file = new FileData();
      file.setName("I" + upperFirst(table.getName()) + "Controller.java");
      file.setContent(code);
      file.setPath(String.format(IXxxController_PATH, modulePrefix, moduleName, basePackageName, moduleName));
      files.add(file);

      // 生成Controller
      data.put("primaryField", table.getPrimaryColumn());
      data.put("primaryFieldType", table.getPrimaryType());
      code = FreemarkerUtils.getFtlToString("/service/XxxController", data);
      file = new FileData();
      file.setName(upperFirst(table.getName()) + "Controller.java");
      file.setContent(code);
      file.setPath(String.format(XxxController_PATH, modulePrefix, moduleName, basePackageName, moduleName));
      files.add(file);

      // 生成Client
      code = FreemarkerUtils.getFtlToString("/client/XxxClient", data);
      file = new FileData();
      file.setName(upperFirst(table.getName()) + "Client.java");
      file.setContent(code);
      file.setPath(String.format(XxxClient_PATH, modulePrefix, moduleName, basePackageName, moduleName));
      files.add(file);

      // 生成Service
      code = FreemarkerUtils.getFtlToString("/service/IXxxService", data);
      file = new FileData();
      file.setName("I" + upperFirst(table.getName()) + "Service.java");
      file.setContent(code);
      file.setPath(String.format(IXxxService_PATH, modulePrefix, moduleName, basePackageName, moduleName));
      files.add(file);

      // 生成ServiceImpl
      data.put("primaryField", table.getPrimaryColumn());
      code = FreemarkerUtils.getFtlToString("/service/XxxServiceImpl", data);
      file = new FileData();
      file.setName(upperFirst(table.getName()) + "ServiceImpl.java");
      file.setContent(code);
      file.setPath(String.format(XxxServiceImpl_PATH, modulePrefix, moduleName, basePackageName, moduleName));
      files.add(file);

      // 生成Mapper
      code = FreemarkerUtils.getFtlToString("/service/XxxMapper", data);
      file = new FileData();
      file.setName(upperFirst(table.getName()) + "Mapper.java");
      file.setContent(code);
      file.setPath(String.format(XxxMapper_PATH, modulePrefix, moduleName, basePackageName, moduleName));
      files.add(file);

      // 生成mapper xml
      data.put("tableName", table.getTableName());
      data.put("fieldList", table.getColumnList());
      data.put("primaryField", table.getPrimaryColumn());
      data.put("primaryFieldType", table.getPrimaryType());
      code = FreemarkerUtils.getFtlToString("/service/xxx.xml", data);
      file = new FileData();
      file.setName(table.getName() + ".xml");
      file.setContent(code);
      file.setPath(String.format(XxxMapperXml_PATH, modulePrefix, moduleName));
      files.add(file);
    }

    // 生成代码文件
    File filePath = null;
    FileOutputStream fos = null;
    StringBuffer basePath = new StringBuffer();
    basePath.append(this.codeInfo.getOutputPath()).append("/");
    basePath.append(this.codeInfo.getProjectName()).append("/");
    basePath.append(this.codeInfo.getModulePrefix());
    basePath.append(this.codeInfo.getModuleName()).append("/");
    String path = null;
    for (FileData fd : files) {
      // 创建目录接口
      path = basePath + fd.getPath().replace(".", "/") + "/";
      filePath = new File(path);
      if(!filePath.exists()){
        filePath.mkdirs();
      }

      fos = new FileOutputStream(path + fd.getName(), true);
      fos.write(fd.getContent().getBytes("UTF-8"));
      fos.flush();
      fos.close();
      System.out.println(String.format("生成%s，完成。", fd.getName()));
    }
  }

  private CodeGenerator tableInfo(List<String> tables) throws SQLException, ClassNotFoundException {

    ResultSet rs = null;
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      conn = getConnection();
      DatabaseMetaData dbmd = conn.getMetaData();

      // 处理所有表
      TableInfo tableInfo = null;
      this.tableInfos = new ArrayList<TableInfo>();
      for (String table : tables) {
        // 获取表的主键PK
        String pk = null;
        String pkType = null;
        String comment = "";

        ResultSet tbrs = dbmd.getTables(conn.getCatalog(), "%", table, new String[]{"TABLE"});
        while (tbrs.next()) {
          comment = tbrs.getString("REMARKS");
        }
        ResultSet pkrs = dbmd.getPrimaryKeys(conn.getCatalog(), null, table);
        while (pkrs.next()) {
          pk = pkrs.getString(4);
        }

        // 获取表中所有字段
        List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
        rs = dbmd.getColumns(null, "%", table, "%");
        while (rs.next()) {
          // 是否是主键，默认不是
          boolean isPrimary = false;
          String column_name = rs.getString("COLUMN_NAME");
          String remark = rs.getString("REMARKS");

          /**
           * 默认以自增长列为主键。
           * 没有自增长列时以primaryKey为主键。
           * 两者都没有的，以第一列为主键，暂不考虑。
           */
          boolean isAuto = rs.getString("IS_AUTOINCREMENT").toUpperCase().equals("YES");
          if (isAuto) {
            pk = column_name;
            isPrimary = true;
          } else if (column_name.equals(pk)) {
            isPrimary = true;
          }

          // sql type
          int type = rs.getInt("DATA_TYPE");
          if (isPrimary) {
            pkType = this.jdbcTypMap.get(type).getSimpleName();
          }

          columns.add(
                  new ColumnInfo()
                          .setName(column_name)
                          .setComment(remark)
                          .setJavaTypeName(this.jdbcTypMap.get(type).getSimpleName())
                          .setPrimary(isPrimary)
          );
        }
        if (columns.size() == 0) {
          throw new RuntimeException("表中无有效字段，无法生成代码。");
        }

        tableInfo = new TableInfo();
        tableInfo.setTableName(table);
        tableInfo.setName(parseCamelCase(table.replace(this.codeInfo.getSkipTablePrefix(), "")));
        tableInfo.setComments(comment);
        tableInfo.setColumnList(columns);
        tableInfo.setPrimaryColumn(pk);
        tableInfo.setPrimaryType(pkType);
        tableInfos.add(tableInfo);
      }
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (ps != null) {
        ps.close();
      }
      if (conn != null && !conn.isClosed()) {
        conn.close();
      }
    }

    return this;
  }

  public CodeGenerator() {
    initTypMap();
  }

  public CodeInfo getCodeInfo() {
    return codeInfo;
  }

  public CodeGenerator setCodeInfo(CodeInfo codeInfo) {
    this.codeInfo = codeInfo;
    return this;
  }

  public DatabaseInfo getDatabaseInfo() {
    return databaseInfo;
  }

  public CodeGenerator setDatabaseInfo(DatabaseInfo databaseInfo) {
    this.databaseInfo = databaseInfo;
    return this;
  }

  public List<TableInfo> getTableInfos() {
    return tableInfos;
  }

  public CodeGenerator setTableInfos(List<TableInfo> tableInfos) {
    this.tableInfos = tableInfos;
    return this;
  }

  public CodeGenerator addSkipField(String field) {
    this.skipField.add(field);
    return this;
  }

  public List<ColumnInfo> removeSkipField(List<ColumnInfo> columns) {
    List<ColumnInfo> list = new ArrayList<ColumnInfo>();
    for (ColumnInfo ci : columns) {
      if (skipField.contains(ci.getName())) {
        continue;
      }
      list.add(ci);
    }

    return list;
  }

  public String upperFirst(String str) {
    char[] chars = str.toCharArray();
    if (Character.isLowerCase(chars[0])) {
      chars[0] = Character.toUpperCase(chars[0]);
    }
    return new String(chars);
  }

  private String parseCamelCase(String name){
    char[] chars = name.toCharArray();
    boolean isNextNeedUpper = false;
    for(int i = 0;i < chars.length;i++){
      if(isNextNeedUpper){
        chars[i] = Character.toUpperCase(chars[i]);
        isNextNeedUpper = false;
      }
      if("_".equals(String.valueOf(chars[i]))){
        isNextNeedUpper = true;
      }
    }

    return String.valueOf(chars).replace("_", "");
  }

  private Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName(this.databaseInfo.getDriver());
    return DriverManager.getConnection(
            this.databaseInfo.getUrl()+"&&useInformationSchema=true",
            this.databaseInfo.getUsername(),
            this.databaseInfo.getPassword());
  }

  private void initTypMap() {
    jdbcTypMap = new HashMap<Integer, Class>();
    jdbcTypMap.put(Types.BIGINT, Long.class);
    jdbcTypMap.put(Types.BINARY, byte[].class);
    jdbcTypMap.put(Types.BIT, Boolean.class);
    jdbcTypMap.put(Types.BLOB, byte[].class);
    jdbcTypMap.put(Types.CHAR, String.class);
    jdbcTypMap.put(Types.CLOB, String.class);
    jdbcTypMap.put(Types.DATE, java.sql.Date.class);
    jdbcTypMap.put(Types.DECIMAL, Double.class);
    jdbcTypMap.put(Types.DOUBLE, Double.class);
    jdbcTypMap.put(Types.FLOAT, Double.class);
    jdbcTypMap.put(Types.INTEGER, Integer.class);
    jdbcTypMap.put(Types.JAVA_OBJECT, Object.class);
    jdbcTypMap.put(Types.LONGVARBINARY, byte[].class);
    jdbcTypMap.put(Types.LONGVARCHAR, String.class);
    jdbcTypMap.put(Types.NUMERIC, Double.class);
    jdbcTypMap.put(Types.OTHER, Object.class);
    jdbcTypMap.put(Types.REAL, Float.class);
    jdbcTypMap.put(Types.SMALLINT, Integer.class);
    jdbcTypMap.put(Types.TIME, java.sql.Time.class);
    jdbcTypMap.put(Types.TIMESTAMP, java.sql.Timestamp.class);
    jdbcTypMap.put(Types.TINYINT, Integer.class);
    jdbcTypMap.put(Types.VARBINARY, byte[].class);
    jdbcTypMap.put(Types.VARCHAR, String.class);
  }

  private Map<Integer, Class> jdbcTypMap;

  /**
   * 代码基本信息
   */
  private CodeInfo codeInfo;
  /**
   * 数据库信息
   */
  private DatabaseInfo databaseInfo;
  /**
   * 表结构信息
   */
  private List<TableInfo> tableInfos;
  /**
   * base model中存在，其他model中不需要生成
   */
  private Set<String> skipField = new HashSet<String>();

  private static final String XxxMODEL_PATH = "%s%s-common/src/main/java/%s/%s/common/model";
  private static final String IXxxController_PATH = "%s%s-common/src/main/java/%s/%s/common/controller";
  private static final String XxxClient_PATH = "%s%s-client-starter/src/main/java/%s/%s/client";
  private static final String XxxController_PATH = "%s%s-service/src/main/java/%s/%s/controller";
  private static final String IXxxService_PATH = "%s%s-service/src/main/java/%s/%s/service";
  private static final String XxxServiceImpl_PATH = "%s%s-service/src/main/java/%s/%s/service/impl";
  private static final String XxxMapper_PATH = "%s%s-service/src/main/java/%s/%s/mapper";
  private static final String XxxMapperXml_PATH = "%s%s-service/src/main/resources/mybatis/mapper";
}
