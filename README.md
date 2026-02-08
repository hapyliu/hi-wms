# hi-wms

本项目基于 [RuoYi-Vue springboot3 分支](https://gitee.com/y_project/RuoYi-Vue/tree/springboot3) 作为脚手架思路整理，并升级到以下技术栈：

- Spring Boot 4.0.2
- JDK 25（最新修复版本）
- PostgreSQL 16.11
- MyBatis-Plus ORM

## 本地运行

1. 安装 JDK 25、PostgreSQL 16.11。
2. 创建数据库并配置账号密码。
3. 修改 `src/main/resources/application.yml` 中的数据库连接。
4. 启动应用：

```bash
mvn spring-boot:run
```

## 目录结构

- `src/main/java/com/hiwms`：应用主代码
- `src/main/resources`：配置文件与资源

后续可继续对照 RuoYi-Vue 的模块拆分方式逐步迁移业务功能。
