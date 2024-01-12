<template>
  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../../assets/logo.png" class="logo" />
      </div>
      <el-form
        ref="loginFormRef"
        class="login_form"
        :model="loginForm"
        label-width="0px"
      >
        <el-form-item>
          <el-input
            prefix-icon="iconfont icon-user"
            placeholder="请输入用户名"
            v-model="loginForm.username"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            prefix-icon="iconfont icon-3702mima"
            placeholder="请输入密码"
            type="password"
            v-model="loginForm.password"
          ></el-input>
        </el-form-item>
        <el-form-item class="verifycode">
          <el-row :gutter="20">
            <el-col :span="30">
              <el-input
                prefix-icon="iconfont icon-3702mima"
                placeholder="请输入验证码"
                v-model="loginForm.code"
              ></el-input>
            </el-col>
            <el-col :span="5">
              <img :src="codeUrl" width="95" @click="getCodeImge" />
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="info" @click="resetLoginForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  data() {
    return {
      codeUrl:"",
      loginForm: {
        uuid:"",
        username: "admin",
        password: "123",
        code: "",
      },
    };
  },
  created: function(){
    this.getCodeImge();
  },
  methods: {
    async getCodeImge() {
      const { data: res } = await this.$http.get("code");
      console.log(res)
      if (res.code != 200) {
        console.log("获取验证码失败");
      } else {
        console.log("获取验证码成功");
        this.codeUrl = "data:image/gif;base64," + res.data.img;
        this.loginForm.uuid = res.data.uuid;
      }
    },
    
    resetLoginForm: function () {
      //   this.$refs.loginFormRef.resetFields();
      this.loginForm = { username: "", password: "", code: "" };
    },
    async login() {
      const { data: res } = await this.$http.post("login", this.loginForm);
      console.log(res);
      if (res.code != 200) {
        console.log("登录失败");
      } else {
        console.log("登录成功");
        window.sessionStorage.setItem("userId", res.data.id);
        window.sessionStorage.setItem("userName", res.data.name);
        this.$router.push("/main");
      }
    },
  },
};
</script>
<style>
@import "../../assets/css/login/login.css";
</style>