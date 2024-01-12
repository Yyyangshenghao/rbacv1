import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import router from './router'
import "./assets/css/global.css"
import "./assets/fonts/iconfont.css"
import axios from 'axios'
import {Message,MessageBox} from 'element-ui'

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.prototype.$http=axios
axios.defaults.baseURL="http://127.0.0.1:8080/api/"
axios.defaults.withCredentials = true
Vue.prototype.$message=Message
Vue.prototype.$confirm=MessageBox

// 请求拦截
axios.interceptors.request.use(function(request){
      const userId = window.sessionStorage.getItem("userId");
      if(userId){
        request.headers.userId=userId;
      }
      return request;
},function(err){
  return Promise.reject(err)
})

// 响应拦截
axios.interceptors.response.use(function(response){
	if(response.data.code == 401){
    router.push("/login");
	}else if(response.data.code == 403){
    router.push("/nopermission");
  }
	return response
},function(err){
	return Promise.reject(err)
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
