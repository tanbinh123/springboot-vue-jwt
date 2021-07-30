import axios from 'axios'
import router from '../router'

export function request(config) {
    
    const req = axios.create({
      baseURL: 'http://localhost:8081',
      timeout: 5000
    })
  
    // 将所有的网络请求头都带上本地存储的 Token
    req.interceptors.request.use(config => {
      const token = localStorage.getItem('authorization')
      token && (config.headers.authorization = token)
      return config
    })
  
    /**
     * 对所有请求得到的响应结果获取其中的 Token
     * 如果返回不为空, 则说明本地 Token 未失效, 重新设置本地 Token
     * 否则清除本地 Token, 并跳转到 login 界面
     */
    req.interceptors.response.use(response => {
      const token = response.headers.authorization
      const canNoLogin = response.data;
      if (token) {
        localStorage.setItem('authorization', token)
      } else if (canNoLogin.code===200) { 
        console.log(canNoLogin.message);
      } else {
        localStorage.removeItem('authorization')
        router.push('/login')
      }
      return response.data
    })
  
    return req(config)
  }