## 项目简介

基于 VUE/Element-UI 和 Spring Boot/Spring Cloud & Alibaba 前后端技术栈的微服务应用开发框架。


项目托管同步更新GIT资源库：

**[https://github.com/xautlx/entdiy-cloud](https://github.com/xautlx/entdiy-cloud)**

**[https://gitee.com/xautlx/entdiy-cloud](https://gitee.com/xautlx/entdiy-cloud)**

## 工程模块

~~~
com.entdiy 
├── entdiy-api             // 接口模块
│       └── entdiy-api-system                 // 系统接口
│       └── entdiy-api-file                   // 文件接口
├── entdiy-auth            // 认证中心 [9200]
├── entdiy-common          // 通用模块
│       └── entdiy-common-core                // 核心模块
│       └── entdiy-common-datascope           // 权限范围
│       └── entdiy-common-datasource          // 多数据源
│       └── entdiy-common-log                 // 日志记录
│       └── entdiy-common-redis               // 缓存服务
│       └── entdiy-common-security            // 安全模块
│       └── entdiy-common-swagger             // 系统接口
├── entdiy-config          // 全局配置
├── entdiy-gateway         // 网关模块 [8080]
├── entdiy-modules         // 业务模块
│       └── entdiy-allinone                   // 整合运行      [9500]
│       └── entdiy-file                       // 文件服务      [9300]
│       └── entdiy-gen                        // 代码生成      [9202]
│       └── entdiy-job                        // 定时任务      [9203]
│       └── entdiy-system                     // 系统模块      [9201]
├── entdiy-starter         // 组件模块
│       └── entdiy-starter-feign              // Feign组件
│       └── entdiy-starter-flyway             // Flyway组件
│       └── entdiy-starter-logger             // 日志组件
│       └── entdiy-starter-mybatis            // MyBatisPlus组件
│       └── entdiy-starter-redis              // Redis组件
│       └── entdiy-starter-swagger            // Swagger组件
│       └── entdiy-starter-web                // Web整合定义
├── entdiy-ui              // 前端框架 [80]
├── entdiy-visual          // 图形化管理模块
│       └── entdiy-monitor                    // Boot Admin监控  [9100]
│       └── entdiy-monitor                    // Druid SQL监控   [9101]
├── quick-start            // 快速开始
├── pom.xml                // 公共依赖
~~~

注：目录顺序及结构层次以工程导入IDEA显示为依据。

## 在线体验

演示地址：**[http://cloud.entdiy.com/admin](http://cloud.entdiy.com/admin)** 默认登录账户密码：admin/123456

注：目前项目尚未做任何演示锁定控制，所有功能可以随意操作，请勿随意修改数据或密码导致系统不可访问！！！
项目构建服务配置为每天晚上定时自动重建整个演示应用部署，恢复当前代码构建初始状态！！！

## 快速开始

[Quick Start | 一键本地部署体验](./quick-start/README.md) 

[Snapshot | 系统快照截图](./quick-start/Snapshot.md) 

注：目前仅提供一套脚本和配置实现Docker化一键本地部署启动应用，供本地流畅体验整套开发框架的构建部署运行和原型功能。

## 项目参考

* 本项目基于 [RuoYi-Cloud](https://gitee.com/y_project/RuoYi-Cloud) 扩展定制，感谢若依开源项目作者。
本项目保留若依项目原始介绍页面详见： [RuoYi-Cloud.md](./RRuoYi-Cloud.md) 。

* 本项目基于作者工作及之前多个应用开发框架开源项目经验，
 [s2jh4net@gitee.com](https://gitee.com/xautlx/s2jh4net)  / [s2jh4net@github.com](https://github.com/xautlx/s2jh4net) / 
 [s2jh@gitee.com](https://gitee.com/xautlx/s2jh)  / [s2jh@github.com](https://github.com/xautlx/s2jh) ，不断增强优化，敬请关注。
 
* 欢迎关注项目作者其他开源项目：[https://gitee.com/xautlx](https://gitee.com/xautlx) / [https://github.com/xautlx](https://github.com/xautlx) 

