const jsonServer = require('json-server')
const server = jsonServer.create()
const router = jsonServer.router('mock/db.json')
const middlewares = jsonServer.defaults()

// Set default middlewares (logger, static, cors and no-cache)
server.use(middlewares)

// Add custom routes before JSON Server router
server.get('/validate/code', (req, res) => {
  res.json({
    "msg": "操作成功",
    "img": "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",
    "code": 200,
    "uuid": "baea1d07b2df43aab369e360dbbba096"
  })
})
server.post('/auth/login', (req, res) => {
  res.json({
    "code": 200,
    "msg": null,
    "data": {
      "access_token": "000f5d11aa1-a66b-4232-a269-0f0c6a0c7c48",
      "expires_in": 7200
    }
  })
})
server.get('/system/user/getInfo', (req, res) => {
  res.json({
    "msg": "操作成功", "code": 200, "permissions": ["*:*:*"], "roles": ["admin"], "user": {
      "userId": 1,
      "deptId": 103,
      "userName": "admin",
      "nickName": "超级管理员",
      "email": "xautlx@hotmail.com",
      "phonenumber": "15888888888",
      "sex": "1",
      "avatar": "",
      "password": "$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2",
      "salt": null,
      "status": "0",
      "delFlag": "0",
      "loginIp": "127.0.0.1",
      "loginDate": "2021-06-18T21:56:08.000+08:00",
      "createBy": "admin",
      "createTime": "2021-06-18 21:56:08",
      "updateBy": null,
      "updateTime": null,
      "remark": "管理员",
      "params": {},
      "dept": {
        "deptId": 103,
        "parentId": 101,
        "ancestors": null,
        "deptName": "研发部门",
        "orderNum": "1",
        "leader": "演示",
        "phone": null,
        "email": null,
        "status": "0",
        "delFlag": null,
        "parentName": null,
        "createBy": null,
        "createTime": null,
        "updateBy": null,
        "updateTime": null,
        "children": [],
        "params": {}
      },
      "roles": [{
        "roleId": 1,
        "roleName": "超级管理员",
        "roleKey": "admin",
        "roleSort": "1",
        "dataScope": "1",
        "menuCheckStrictly": false,
        "deptCheckStrictly": false,
        "status": "0",
        "delFlag": null,
        "createBy": null,
        "createTime": null,
        "updateBy": null,
        "updateTime": null,
        "remark": null,
        "params": {},
        "flag": false,
        "menuIds": null,
        "deptIds": null,
        "admin": true
      }],
      "roleIds": null,
      "postIds": null,
      "admin": true
    }
  })
})
server.get('/system/menu/getRouters', (req, res) => {
  res.json({
    "msg": "操作成功", "code": 200, "data": [{
      "name": "System",
      "path": "/system",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {"title": "系统管理", "icon": "system", "noCache": false},
      "children": [{
        "name": "User",
        "path": "user",
        "hidden": false,
        "component": "system/user/index",
        "meta": {"title": "用户管理", "icon": "user", "noCache": false}
      }, {
        "name": "Role",
        "path": "role",
        "hidden": false,
        "component": "system/role/index",
        "meta": {"title": "角色管理", "icon": "peoples", "noCache": false}
      }, {
        "name": "Menu",
        "path": "menu",
        "hidden": false,
        "component": "system/menu/index",
        "meta": {"title": "菜单管理", "icon": "tree-table", "noCache": false}
      }, {
        "name": "Dept",
        "path": "dept",
        "hidden": false,
        "component": "system/dept/index",
        "meta": {"title": "部门管理", "icon": "tree", "noCache": false}
      }, {
        "name": "Post",
        "path": "post",
        "hidden": false,
        "component": "system/post/index",
        "meta": {"title": "岗位管理", "icon": "post", "noCache": false}
      }, {
        "name": "Dict",
        "path": "dict",
        "hidden": false,
        "component": "system/dict/index",
        "meta": {"title": "字典管理", "icon": "dict", "noCache": false}
      }, {
        "name": "Config",
        "path": "config",
        "hidden": false,
        "component": "system/config/index",
        "meta": {"title": "参数设置", "icon": "edit", "noCache": false}
      }, {
        "name": "Notice",
        "path": "notice",
        "hidden": false,
        "component": "system/notice/index",
        "meta": {"title": "通知公告", "icon": "message", "noCache": false}
      }]
    }, {
      "name": "Monitor",
      "path": "/monitor",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {"title": "系统监控", "icon": "monitor", "noCache": false},
      "children": [{
        "name": "Online",
        "path": "online",
        "hidden": false,
        "component": "monitor/online/index",
        "meta": {"title": "在线用户", "icon": "online", "noCache": false}
      }, {
        "name": "Operlog",
        "path": "operlog",
        "hidden": false,
        "component": "system/operlog/index",
        "meta": {"title": "操作日志", "icon": "form", "noCache": false}
      }, {
        "name": "Job",
        "path": "job",
        "hidden": false,
        "component": "monitor/job/index",
        "meta": {"title": "定时任务", "icon": "job", "noCache": false}
      }, {
        "name": "Logininfor",
        "path": "logininfor",
        "hidden": false,
        "component": "system/logininfor/index",
        "meta": {"title": "登录日志", "icon": "logininfor", "noCache": false}
      }, {
        "name": "ops",
        "path": "ops",
        "hidden": false,
        "component": "system/ops/index",
        "meta": {"title": "运维监控", "icon": "ops", "noCache": false}
      }]
    }, {
      "name": "Tool",
      "path": "/tool",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {"title": "开发工具", "icon": "tool", "noCache": false},
      "children": [{
        "name": "Build",
        "path": "build",
        "hidden": false,
        "component": "tool/build/index",
        "meta": {"title": "表单构建", "icon": "build", "noCache": false}
      }, {
        "name": "Gen",
        "path": "gen",
        "hidden": false,
        "component": "tool/gen/index",
        "meta": {"title": "代码生成", "icon": "code", "noCache": false}
      }, {
        "name": "/swagger-ui.html",
        "path": "external:///swagger-ui.html",
        "hidden": false,
        "component": "Layout",
        "meta": {"title": "Swagger文档", "icon": "swagger", "noCache": false}
      }, {
        "name": "/doc.html",
        "path": "external:///doc.html",
        "hidden": false,
        "component": "Layout",
        "meta": {"title": "Knife4j文档", "icon": "form", "noCache": false}
      }]
    }, {
      "name": "Ops",
      "path": "/ops",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {"title": "运维管理", "icon": "table", "noCache": false},
      "children": [{
        "name": "/nacos/#/configurationManagement?dataId=&group=&appName=&namespace=MASTER&pageSize=&pageNo=&namespaceShowName=MASTER",
        "path": "external:///nacos/#/configurationManagement?dataId=&group=&appName=&namespace=MASTER&pageSize=&pageNo=&namespaceShowName=MASTER",
        "hidden": false,
        "component": "Layout",
        "meta": {"title": "Nacos控制台", "icon": "nacos", "noCache": false}
      }, {
        "name": "/monitor/wallboard",
        "path": "external:///monitor/wallboard",
        "hidden": false,
        "component": "Layout",
        "meta": {"title": "Admin控制台", "icon": "server", "noCache": false}
      }, {
        "name": "/druid",
        "path": "external:///druid",
        "hidden": false,
        "component": "Layout",
        "meta": {"title": "Druid控制台", "icon": "server", "noCache": false}
      }, {
        "name": "/grafana/explore?orgId=1&left=%5B%22now-1h%22,%22now%22,%22Loki%22,%7B%22expr%22:%22%7Blevel%3D~%5C%22DEBUG%7CERROR%7CINFO%7CWARN%5C%22%7D%22%7D%5D",
        "path": "external:///grafana/explore?orgId=1&left=%5B%22now-1h%22,%22now%22,%22Loki%22,%7B%22expr%22:%22%7Blevel%3D~%5C%22DEBUG%7CERROR%7CINFO%7CWARN%5C%22%7D%22%7D%5D",
        "hidden": false,
        "component": "Layout",
        "meta": {"title": "Grafana控制台", "icon": "tool", "noCache": false}
      }]
    }]
  })
})

// To handle POST, PUT and PATCH you need to use a body-parser
// You can use the one used by JSON Server
server.use(jsonServer.bodyParser)
server.use((req, res, next) => {
  if (req.method === 'POST') {
    req.body.createdAt = Date.now()
  }
  // Continue to JSON Server router
  next()
})

// Use default router
server.use(router)
server.listen(8080, () => {
  console.log('JSON Server is running')
})
