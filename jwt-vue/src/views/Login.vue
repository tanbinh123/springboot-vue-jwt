<template>
  <body id="poster">
    <el-form class="login-container" label-position="left" label-width="0px">
      <h3 class="login_title">系统登录</h3>
      <el-form-item>
        <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-checkbox v-model="loginForm.isRemember" class="remember">记住密码</el-checkbox>
        <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
  import {request} from '../network/request'
  export default {
    name: 'Login',
    data() {
      return {
        loginForm: {
            username: '',
            password: '',
            isRemember: false
        },
      }
    },
    methods: {
      login() {
          request({ method: 'post', url: '/login', data: this.loginForm }).then(res => {
            // 登录成功将用户 uid 存入 localStorage
            localStorage.setItem('uid', res.data)
            // 并跳转到 home 界面
            this.$router.push('/home')
            this.$message('登录成功')
          }).catch(err => {
            console.log(err)
          })
      }
    }
  }
</script>

<style>
  #poster {
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
  body{
    margin: 0px;
  }
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>