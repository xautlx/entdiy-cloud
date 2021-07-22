USE `entdiy_nacos`;
SET NAMES 'utf8';

-- 解决Nacos频繁打印日志问题：https://github.com/alibaba/nacos/issues/4682
INSERT INTO tenant_info (id, kp, tenant_id, tenant_name, tenant_desc, create_source, gmt_create, gmt_modified)
VALUES (1, '1', 'MASTER', 'MASTER', '主命名空间', 'nacos', 1623342702280, 1623342702280);


INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (1, 'application-shared.yaml', 'DEFAULT_GROUP', '# 本地开发共享全局配置，线上环境通过Nacos的shared-configs覆盖配置
#################################################
###      为了演示需要开启了大量开发调试特性       ####
###    实际生产环境部署需要定制关闭相关配置属性    ####
#################################################
spring:
  autoconfigure:
    # 屏蔽默认Datasource配置加载，应用配置中采用dynamic-datasource-spring-boot-starter方式
    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure,
             org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
  redis:
    host: redis-server
    port: 6379
    password: redisP@ssword123
    database: 0
  datasource:
    druid:
      web-stat-filter:
        enabled: true
        url-pattern: "/*"
        exclusions: "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*,/actuator/*"
        session-stat-enable: true
        profile-enable: true
      stat-view-servlet:
        url-pattern: /druid/*
        enabled: true
        allow: ""
        # 由于 druid monitor 的登录校验基于 session 设计，所有在无状态的微服务中不适用。
        # 建议直接暴露所有 druid 相关的端点给网关（但是不可暴露到互联网），通过前置网关统一接口权限。
        #loginUsername: admin
        #loginPassword: 123456
    dynamic:
      druid:
        initial-size: 5
        min-idle: 5
        maxActive: 20
        maxWait: 60000
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQuery: SELECT 1 FROM DUAL
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        filters: stat,slf4j
        connectionProperties: druid.stat.mergeSql\\=true;druid.stat.slowSqlMillis\\=5000
      datasource:
        # 主库数据源
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://mysql-server:3306/entdiy_system?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
          username: entdiy
          password: entdiyP@ssword123
        # 从库数据源
        #slave:
          #driver-class-name:
          #username:
          #password:
          #url:
      # 指定默认数据源名称
      primary: master
  flyway:
    enabled: true
    table: flyway_${spring.application.name}
    baseline-version: 0.0.0
    baseline-on-migrate: true
    validate-on-migrate: false
    clean-disabled: true
    placeholders:
      flyway-table: ${spring.flyway.table}
      spring.application.name: ${spring.application.name}
mybatis-plus:
  mapper-locations: classpath*:mapper/**/*.xml
  #实体扫描，多个package用逗号或者分号分隔
  typeAliasesPackage: com.entdiy.**.domain
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
  global-config:
    banner: false
    #刷新mapper 调试神器
    refresh: true
    db-config:
      #主键类型 @see com.baomidou.mybatisplus.annotation.IdType
      id-type: 3
      #字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
      field-strategy: not_empty
      #驼峰下划线转换
      db-column-underline: true
      #设置表前缀
      #table-prefix: sys_
      #数据库大写下划线转换
      #capital-mode: true
      #序列接口实现类配置
      #key-generator: com.baomidou.springboot.xxx
      #逻辑删除配置
      logic-delete-field: deleted  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logic-delete-value: 1
      logic-not-delete-value: 0
      #数据库类型
      db-type: mysql
    #自定义SQL注入器
    #sql-injector: com.baomidou.mybatisplus.mapper.LogicSqlInjector
    #自定义填充策略接口实现
    #meta-object-handler: com.baomidou.springboot.xxx

#请求处理的超时时间
ribbon:
  ReadTimeout: 10000
  ConnectTimeout: 10000

# feign 配置
feign:
  sentinel:
    enabled: true
  okhttp:
    enabled: true
  httpclient:
    enabled: false
  client:
    config:
      default:
        connectTimeout: 10000
        readTimeout: 10000
  compression:
    request:
      enabled: true
    response:
      enabled: true

# 暴露监控端点
management:
  endpoints:
    web:
      exposure:
        include: ''*''
  endpoint:
    health:
      show-details: ALWAYS
swagger:
  # 当前仅为演示需要开启，生产环境请设置为false
  enabled: true
  title: API接口文档
  license: Powered By EntDIY
  licenseUrl: https://www.entdiy.com
logging:
  config: classpath:logback-spring-prd.xml
  level:
    com.entdiy: DEBUG
    ROOT: WARN
# https://github.com/akkinoc/logback-access-spring-boot-starter
logback:
  access:
    # 完整打印HTTP请求和响应，一般在开发或调需要设置为true，生产环境设置为false
    enabled: true
    config: "classpath:logback-access-spring.xml"
    useServerPortInsteadOfLocalPort: true
    # for Tomcat.
    tomcat:
      # Whether to enable request attributes to work with the RemoteIpValve enabled with "server.useForwardHeaders".
      # Defaults to the presence of the RemoteIpValve.
      enableRequestAttributes: true
', '9794b18039c3bc13b0c50fadbee7303e', '2019-11-29 16:31:20', '2021-07-13 03:02:20', null, '192.168.3.250', '', 'MASTER', '通用配置', 'null', 'null', 'yaml', 'null');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (2, 'entdiy-gateway.yaml', 'DEFAULT_GROUP', 'spring:
  cloud:
    gateway:
      discovery:
        locator:
          lowerCaseServiceId: true
          enabled: true
      routes:
        # 认证中心
        - id: entdiy-auth
          uri: lb://entdiy-auth
          predicates:
            - Path=/auth/**
          filters:
            # 验证码处理
            - CacheRequestFilter
            - ValidateCodeFilter
            - StripPrefix=1
        # 代码生成
        - id: entdiy-gen
          uri: lb://entdiy-gen
          predicates:
            - Path=/code/**
          filters:
            - StripPrefix=1
        # 定时任务
        - id: entdiy-job
          uri: lb://entdiy-job
          predicates:
            - Path=/schedule/**
          filters:
            - StripPrefix=1
        # 系统模块
        - id: entdiy-system
          uri: lb://entdiy-system
          predicates:
            - Path=/system/**
          filters:
            - StripPrefix=1
        # 文件服务
        - id: entdiy-file
          uri: lb://entdiy-file
          predicates:
            - Path=/file/**
          filters:
            - StripPrefix=1
        # Spring Boot监控模块
        - id: entdiy-monitor
          uri: lb://entdiy-monitor
          predicates:
            - Path=/monitor/**
          filters:
            - PreserveHostHeader
        # Druid SQL监控服务
        - id: entdiy-druid
          uri: lb://entdiy-druid
          predicates:
            - Path=/druid/**
          filters:
            - PreserveHostHeader
# 不校验白名单
ignore:
  whites:
    - /*/pub/**
    - /druid/**
    - /monitor/**
    - /auth/logout
    - /auth/login
    - /*/v2/api-docs
    - /csrf
', '37e15b08b15878c45ad713e15e89d66d', '2020-05-14 14:17:55', '2021-07-13 03:46:45', null, '192.168.3.250', '', 'MASTER', '网关模块', 'null', 'null', 'yaml', 'null');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (3, 'entdiy-auth.yaml', 'DEFAULT_GROUP', 'server:
  port: 9200
', '332d0f8b5772b5c27c6e1c06afac2d72', '2020-11-20 00:00:00', '2021-05-24 00:35:47', null, '172.30.0.1', '', 'MASTER', '认证中心', 'null', 'null', 'yaml', 'null');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (4, 'entdiy-monitor.yaml', 'DEFAULT_GROUP', 'server:
  port: 9100

spring:
  security:
    user:
      name: admin
      password: 123456
  boot:
    admin:
      ui:
        title: 服务状态监控
      discovery:
        ignored-services:
          - entdiy-monitor
          - entdiy-druid
  thymeleaf:
    check-template-location: false
    check-template: false
logging:
  level:
    ROOT: WARN', '0f1fc76d14ce6808376cf28adde7f797', '2020-11-20 00:00:00', '2021-07-13 03:15:22', null, '192.168.3.250', '', 'MASTER', '监控中心', 'null', 'null', 'yaml', 'null');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (5, 'entdiy-system.yaml', 'DEFAULT_GROUP', 'server:
  port: 9201
spring:
  datasource:
    dynamic:
      datasource:
        master:
          url: jdbc:mysql://mysql-server:3306/entdiy_system?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
          username: entdiy
          password: entdiyP@ssword123
', 'b892f3518df9d8b4d91626d36830b8e7', '2020-11-20 00:00:00', '2021-06-10 10:46:03', null, '172.30.0.1', '', 'MASTER', '系统模块', 'null', 'null', 'yaml', 'null');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (6, 'entdiy-gen.yaml', 'DEFAULT_GROUP', 'server:
  port: 9202
spring:
  datasource:
    dynamic:
      datasource:
        master:
          url: jdbc:mysql://mysql-server:3306/entdiy_system?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
          username: entdiy
          password: entdiyP@ssword123
# 代码生成
gen:
  # 作者
  author: Li Xia <xautlx@hotmail.com>
  # 默认生成包路径 system 需改成自己的模块名称 如 system monitor tool
  packageName: com.entdiy.system
  # 自动去除表前缀，默认是false
  autoRemovePre: false
  # 表前缀（生成类名不会包含表前缀，多个用逗号分隔）
  tablePrefix: sys_
', '18c4117c0b7556dd0844abbed2ede598', '2020-11-20 00:00:00', '2021-05-21 00:32:25', null, '172.30.0.1', '', 'MASTER', '代码生成', 'null', 'null', 'yaml', 'null');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (7, 'entdiy-job.yaml', 'DEFAULT_GROUP', 'server:
  port: 9203
spring:
  datasource:
    dynamic:
      datasource:
        master:
          url: jdbc:mysql://mysql-server:3306/entdiy_job?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
          username: entdiy
          password: entdiyP@ssword123
', '6167502c15524b18c478a1864b076d1e', '2020-11-20 00:00:00', '2021-05-24 00:03:55', null, '172.30.0.1', '', 'MASTER', '定时任务', 'null', 'null', 'yaml', 'null');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (8, 'entdiy-file.yaml', 'DEFAULT_GROUP', 'server:
  port: 9300

# 本地文件上传
file:
  domain: http://127.0.0.1:9300
  path: D:/entdiy/uploadPath
  prefix: /statics

# FastDFS配置
#fdfs:
#  domain: http://8.129.231.12
#  soTimeout: 3000
#  connectTimeout: 2000
#  trackerList: 8.129.231.12:22122

# Minio配置
#minio:
#  url: http://8.129.231.12:9000
#  accessKey: minioadmin
#  secretKey: minioadmin
#  bucketName: test
', 'd3374ca6690b15b2714c4965e026de61', '2020-11-20 00:00:00', '2021-05-24 00:37:08', null, '172.30.0.1', '', 'MASTER', '文件服务', 'null', 'null', 'yaml', 'null');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (9, 'entdiy-gateway-sentinel.json', 'DEFAULT_GROUP', '[
    {
        "resource": "entdiy-auth",
        "count": 500,
        "grade": 1,
        "limitApp": "default",
        "strategy": 0,
        "controlBehavior": 0
    },
	{
        "resource": "entdiy-system",
        "count": 1000,
        "grade": 1,
        "limitApp": "default",
        "strategy": 0,
        "controlBehavior": 0
    },
	{
        "resource": "entdiy-gen",
        "count": 200,
        "grade": 1,
        "limitApp": "default",
        "strategy": 0,
        "controlBehavior": 0
    },
	{
        "resource": "entdiy-job",
        "count": 300,
        "grade": 1,
        "limitApp": "default",
        "strategy": 0,
        "controlBehavior": 0
    },
	{
        "resource": "entdiy-file",
        "count": 500,
        "grade": 1,
        "limitApp": "default",
        "strategy": 0,
        "controlBehavior": 0
    }
]', '9f3a3069261598f74220bc47958ec252', '2020-11-20 00:00:00', '2020-11-20 00:00:00', null, '0:0:0:0:0:0:0:1', '', 'MASTER', '限流策略', 'null', 'null', 'json', 'null');
INSERT INTO config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (10, 'entdiy-druid.yaml', 'DEFAULT_GROUP', 'server:
  port: 9101
monitor:
  #需要监控的服务名spring.application.name
  applications:
    - entdiy-system
    - entdiy-job
    - entdiy-gen
  #监控页面的登录用户名和密码
  login-username: admin
  login-password: 123456
logging:
  level:
    ROOT: WARN', '5a7e465e541fe192943ca45059a917b2', '2021-07-06 10:28:27', '2021-07-13 03:15:08', null, '192.168.3.250', '', 'MASTER', '', '', '', 'yaml', '');
