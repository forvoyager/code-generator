# 代码生成工具
用于在项目开发过程中生成mapper, mapper xml, dao, service, controller等基础代码，让开发人员不必花费过多的时间在这些基础代码的开发上。

目前生成的是微服务架构代码格式，如果需要MVC风格代码，忽略掉*-client-starter模块的内容即可。

# 使用方法
按照项目/模块的需要，配置好项目信息、作者信息及数据库信息，然后添加需要生成基础代码的表，直接运行（com.xr.code.generate.CodeGenerator）即可。
``` java
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
tables.add("ms_user_level");
```

# 最终生成的代码样例
```
micro_service
  └─ms-account
      ├─ms-account-client-starter
      │  └─src
      │      └─main
      │          └─java
      │              └─com
      │                  └─xr
      │                      └─account
      │                          └─client
      │                                  AccountClient.java
      │                                  UserLevelClient.java
      │
      ├─ms-account-common
      │  └─src
      │      └─main
      │          └─java
      │              └─com
      │                  └─xr
      │                      └─account
      │                          └─common
      │                              ├─controller
      │                              │      IAccountController.java
      │                              │      IUserLevelController.java
      │                              │
      │                              └─model
      │                                      AccountModel.java
      │                                      UserLevelModel.java
      │
      └─ms-account-service
          └─src
              └─main
                  ├─java
                  │  └─com
                  │      └─xr
                  │          └─account
                  │              ├─controller
                  │              │      AccountController.java
                  │              │      UserLevelController.java
                  │              │
                  │              ├─mapper
                  │              │      AccountMapper.java
                  │              │      UserLevelMapper.java
                  │              │
                  │              └─service
                  │                  │  IAccountService.java
                  │                  │  IUserLevelService.java
                  │                  │
                  │                  └─impl
                  │                          AccountServiceImpl.java
                  │                          UserLevelServiceImpl.java
                  │
                  └─resources
                      └─mybatis
                          └─mapper
                                  account.xml
                                  userLevel.xml
```

注意：
* 默认所有表都有如下三个字段
    ```
    create_time 创建时间
    update_time 最后修改时间
    version 数据版本号（用于乐观锁实现）
    ```
* 项目中如下路径的文件是mybatis基础配置文件，根据需要自行调整。
    ```
    code-generator/src/main/resources/mybatis
    ```
* 项目中如下路径的文件是mapper和service的基础文件，根据需要自行调整。
    ```
    code-generator/src/main/java/com/xr/base/core/service
    ```




