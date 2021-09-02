<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">

      <div class="logo-wrapper">
        <div class="logo">
          <img :src="logo" alt="logo"> 易点.微服务应用开发框架
        </div>
      </div>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img"/>
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>

    </el-form>

    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2021 entdiy.com All Rights Reserved.</span>
      <span style="padding-left: 10px">
        <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=50010602502888" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;">
        <img :src="beianImg" style="float:left;"/>渝公网安备 50010602502888号
        </a>
      </span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'
import logo from '@/assets/logo/logo.png'
import beianImg from '@/assets/images/beian.png'

export default {
  name: "Login",
  data() {
    return {
      logo,
      beianImg,
      codeUrl: "",
      cookiePassword: "",
      loginForm: {
        username: "admin",
        password: "123456",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "用户名不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        code: [{ required: true, trigger: "change", message: "验证码不能为空" }]
      },
      loading: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();

    // 仅演示说明之用，实际项目直接移除即可
    this.$notify.info({
      position: 'bottom-right',
      duration: 0,
      title: '演示访问异常操作指南',
      dangerouslyUseHTMLString: true,
      message:
        '<p>当前演示部署与开源代码内容完全一致，未做任何演示操作锁定控制，请勿修改密码之类操作。</p>' +
        '<p>如果遇到无法登录或系统内部数据混乱，请访问 ' +
        '<a style="color: blue" href="http://tool.entdiy.com/jenkins/job/demo-entdiy-cloud/" target="_blank">项目演示Jenkins构建服务</a> ' +
        '触发全自动构建重置演示应用。' +
        '触发构建后大约几分钟即可恢复正常登录和初始数据状态。</p>'
    });

  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = "data:image/gif;base64," + res.img;
        this.loginForm.uuid = res.uuid;
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            this.getCode();
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}

.logo-wrapper{
  position: relative;
  height: 42px;
  background: #fff;
  box-sizing: border-box;
  border-bottom: 1px solid #f1e8e8;
  margin-bottom: 30px;
}
.logo{
  position: absolute;
  left: 0px;
  top: 6px;
  line-height: 30px;
  color: #707070;
  font-weight: 600;
  font-size: 22px;
  white-space: nowrap;
  > img{
    height: 30px;
    vertical-align: top;
  }
}
</style>
