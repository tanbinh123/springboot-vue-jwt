<template>
  <el-row type="flex" justify="center" align="middle" class="main">
    <el-col :span="12">
      <el-button @click="exit">注销</el-button>
      <el-button @click="test">测试</el-button>
    </el-col>
  </el-row>
</template>

<script>
import {request} from '../network/request'
export default {
  name: 'Home',
  methods: {
    exit() {
      request({ method: 'get', url: '/exit',
        params: {
          uid: localStorage.getItem('uid')
        }
      }).then(() => {
        //用户注销后清除本地 Token
        localStorage.removeItem('authorization')
        //并跳转到登录界面
        this.$router.push('/login')
        this.$message('注销成功')
      }).catch(err => {
        console.log(err)
      })
    },
    test() {
      request({ method: 'get', url: '/hello', }).then(res => {
        console.log(res);
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
.main {
  height: 200px;
  text-align: center;
}
</style>