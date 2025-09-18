# oa-system-demo

这是一个基于 Spring Boot 的办公自动化（OA）系统演示项目，旨在提供基础的用户认证、权限控制和 API 文档功能。

## 项目结构

- `oa-server`: 主要功能模块，包含完整的后端实现
- ~~`os-server`~~: 已移除的冗余模块

## 技术栈

- Spring Boot 2.7.17
- Java 17
- MyBatis Plus 3.5.3.1
- Spring Security + JWT
- Swagger UI (Springfox 3.0.0)
- MySQL 8.0.33

## 功能特性

- 用户身份认证（JWT）
- 基于 Spring Security 的访问控制
- Swagger UI 接口文档展示
- 数据库初始化支持

## 快速开始

1. 确保已安装 JDK 17、Maven 和 MySQL 8.0
2. 在 MySQL 中创建数据库
3. 修改 `oa-server/src/main/resources/application.yml` 中的数据库连接配置
4. 运行项目：
   ```bash
   cd oa-server
   mvn spring-boot:run
   ```

## API 测试

项目启动后，可以通过以下端点进行测试：
- Swagger UI: http://localhost:8080/swagger-ui.html
- 登录接口: POST http://localhost:8080/authenticate
- 测试接口: GET http://localhost:8080/hello
- 管理员接口: GET http://localhost:8080/admin

默认用户: admin/admin

## Git 推送问题解决

如果您在推送代码到 GitHub 时遇到 SSH 密钥问题，请参考 `GIT_SSH解决方案.md` 文件中的详细解决方案。