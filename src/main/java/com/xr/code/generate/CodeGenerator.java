package com.xr.code.generate;

import com.xr.code.generate.model.CodeInfo;
import com.xr.code.generate.model.ColumnInfo;
import com.xr.code.generate.model.DatabaseInfo;
import com.xr.code.generate.model.TableInfo;
import com.xr.code.generate.util.FreemarkerUtils;

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
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * Created by forvoyager@outlook.com on 2019-01-31 17:21.
 */
public class CodeGenerator {
  public static void main(String[] args) throws Exception {
    // 项目名称
    String projectName = "micro_service";
    // 模块名称
    String moduleName = "account";
    // 作者
    String author = "forvoyager@outlook.com";
    // 代码存放路径
    String outputPath = "./code";

    // 数据库配置
    String url = "jdbc:mysql://localhost:3306/ms_account_db?characterEncoding=UTF-8&&useInformationSchema=true";
    String driver = "com.mysql.jdbc.Driver";
    String username = "root";
    String password = "123456";
    // 需要去掉的表前缀
    String prefix="ms_";

    // 需要生成代码的表Map<tableName, comment>
    List<String> tables = new ArrayList<String>();
    tables.add("ms_account");

    // 构建生成代码的数据
    new CodeGenerator()
            .setCodeInfo(
                    new CodeInfo()
                    .setProjectName(projectName)
                    .setModuleName(moduleName)
                    .setAuthor(author)
                    .setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                    .setPrefix(prefix)
                    .setOutputPath(outputPath)
            )
            .setDatabaseInfo(
                    new DatabaseInfo()
                    .setUrl(url)
                    .setDriver(driver)
                    .setUsername(username)
                    .setPassword(password)
            )
            .tableInfo(tables)
            .generate();

  }

  /**
   * 生成代码
   */
  private void generate() throws Exception {

    Map data = new HashMap();
    data.put("author", this.codeInfo.getAuthor());
    data.put("time", this.codeInfo.getTime());

    // 生成代码信息
    for(TableInfo table : tableInfos){
      data.put("comments", table.getComments());

      // 生成Model信息
      data.put("moduleName", table.getName());
      data.put("fieldList", table.getColumnList());
      String code = FreemarkerUtils.getFtlToString("XxxModel", data);
      System.out.println(code);
    }

    // 生成代码文件
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
      for(String table : tables){
        // 获取表的主键PK
        String pk = null;
        String comment = "";

        ResultSet tbrs = dbmd.getTables(conn.getCatalog(), "%", table, new String[] { "TABLE" });
        while (tbrs.next()){
          comment = tbrs.getString("REMARKS");
        }
        ResultSet pkrs = dbmd.getPrimaryKeys(conn.getCatalog(), null, table);
        while (pkrs.next()){
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
          if(isAuto){
            pk = column_name;
            isPrimary = true;
          }else if(column_name.equals(pk)){
            isPrimary = true;
          }

          // sql type
          int type = rs.getInt("DATA_TYPE");

          columns.add(
                  new ColumnInfo()
                          .setName(upperFirst(column_name))
                          .setComment(remark)
                          .setJavaTypeName(this.jdbcTypMap.get(type).getSimpleName())
                          .setPrimary(isPrimary)
          );
        }
        if(columns.size() == 0){
          throw new RuntimeException("表中无有效字段，无法生成代码。");
        }

        tableInfo = new TableInfo();
        tableInfo.setTableName(table);
        tableInfo.setName(upperFirst(table.replace(this.codeInfo.getPrefix(), "")));
        tableInfo.setComments(comment);
        tableInfo.setColumnList(columns);
        tableInfo.setPrimaryColumn(pk);
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

  public CodeGenerator(){
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

  private String upperFirst(String str){
    char[] chars = str.toCharArray();
    chars[0] = Character.toUpperCase(chars[0]);
    return String.valueOf(chars);
  }

  private Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName(this.databaseInfo.getDriver());
    return DriverManager.getConnection(this.databaseInfo.getUrl(), this.databaseInfo.getUsername(), this.databaseInfo.getPassword());
  }

  private void initTypMap(){
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
}
