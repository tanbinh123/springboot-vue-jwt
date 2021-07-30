import Vue from 'vue'
import Router from 'vue-router'
import Index from '../views/Index.vue'
import Login from '../views/Login.vue'
import Home from '../views/Home'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
  ]
})

// 设置全局的前置导航守卫
router.beforeEach((to, from, next) => {
  // 如果跳转的目的路径是 login 界面, 不做操作
  if (to.path === '/login') {
    next()
  } else if (to.path === '/') {
    next()
  } else {
    /**
     * 如果是其他界面, 判断本地是否存在 Token
     * 如果存在, 则正常跳转
     * 否则重定向到 login 界面
     */
    let token = localStorage.getItem('authorization')
    if (!token) {
      next('/login')
    } else {
      next()
    }
  }
})

export default router
