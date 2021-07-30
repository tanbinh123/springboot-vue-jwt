<template>
  <div class="index">
    主页
    <el-button @click="toLogin">跳转到登录</el-button>
    <el-button @click="test1">展示</el-button>
    <el-button @click="test2">测试</el-button>
  </div>
</template>

<script>
import {request} from '../network/request'
export default {
  name: 'Index',
  methods: {
    toLogin() {
      this.$router.push('/login')
    },
    test1() {
      request({ method: 'get', url: '/excHello', }).then(res => {
        if (res.message) {
          this.$message(res.message)
        }
      }).catch(err => {
        console.log(err)
      })
    },
    test2() {
      request({ method: 'get', url: '/hello', }).then(res => {
        if (res) {
          //Token未过期则会正常返回'Hello, world!'信息
          this.$message(res)
        } else {
          //否则提示用户登录
          this.$message('请重新登录')
        }
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
.index {
  height: 200px;
  text-align: center;
}
</style>