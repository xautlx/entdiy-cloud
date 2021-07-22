<template>
  <div class="app-container">
    <el-alert
      title="登录账号密码说明"
      type="info"
      effect="dark"
      :closable="false"
      show-icon>
      系统初始化所有监控管理应用的默认账号和密码是：
      <el-tag type="info" effect="dark">admin/123456</el-tag>
      生产环境请注意及时修改设置复杂密码！！！
    </el-alert>
    <el-divider></el-divider>
    <el-row :gutter="20" style="padding-bottom: 20px">
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>Alibaba Nacos 控制台</span>
          </div>
          <div :key="o" class="text item">
            <p>
              一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。
            </p>
            <p>
              官网：<a href="https://nacos.io/zh-cn/" target="_blank">https://nacos.io/zh-cn/</a>
            </p>
            <el-link :underline="false" type="primary" target="_blank"
                     href="/nacos/#/configurationManagement?dataId=&group=&appName=&namespace=MASTER&pageSize=&pageNo=&namespaceShowName=MASTER">
              <el-button type="primary">Alibaba Nacos 控制台</el-button>
            </el-link>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>Alibaba Druid 控制台</span>
          </div>
          <div :key="o" class="text item">
            <p>
              Druid 是一个 JDBC 组件库，包含数据库连接池、SQL Parser 等组件, 被大量业务和技术产品使用或集成，经历过最严苛线上业务场景考验，是你值得信赖的技术产品。
            </p>
            <p>
              官网：<a href="https://github.com/alibaba/druid/wiki/%E9%A6%96%E9%A1%B5" target="_blank">https://github.com/alibaba/druid/wiki/首页</a>
            </p>
            <el-link :underline="false" type="primary" target="_blank"
                     href="/druid/sql.html">
              <el-button type="primary">Alibaba Druid 控制台</el-button>
            </el-link>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>Spring Boot Admin 控制台</span>
          </div>
          <div :key="o" class="text item">
            <p>
              基于Actuator提供的JSON格式Spring Boot应用监控数据接口， Spring Boot Admin扩展提供一套基于图像化的微服务监控数据展示和管理操作功能。
            </p>
            <p>
              官网：<a href="https://github.com/codecentric/spring-boot-admin" target="_blank">https://github.com/codecentric/spring-boot-admin</a>
            </p>
            <el-link :underline="false" type="primary" target="_blank"
                     href="/monitor/wallboard">
              <el-button type="primary">Spring Boot Admin 控制台</el-button>
            </el-link>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>Loki/Grafana 控制台</span>
          </div>
          <div :key="o" class="text item">
            <p>
              Loki 是 Grafana Labs 团队最新的开源项目，是一个水平可扩展，高可用性，多租户的日志聚合系统。
              它的设计非常经济高效且易于操作，因为它不会为日志内容编制索引，而是为每个日志流配置一组标签。
            </p>
            <p>
              官网：<a href="https://github.com/grafana/loki" target="_blank">https://github.com/grafana/loki</a>
            </p>
            <el-link :underline="false" type="primary" target="_blank"
                     href="/grafana/explore?orgId=1&left=%5B%22now-1h%22,%22now%22,%22Loki%22,%7B%22expr%22:%22%7Blevel%3D~%5C%22DEBUG%7CERROR%7CINFO%7CWARN%5C%22%7D%22%7D%5D">
              <el-button type="primary">Loki/Grafana 控制台</el-button>
            </el-link>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  name: "Ops",
  data() {
    return {};
  },
  methods: {}
};
</script>
