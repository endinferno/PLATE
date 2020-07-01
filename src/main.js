import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './vuex';
// import Vuex from 'vuex';

Vue.use(ElementUI);
// Vue.use(Vuex);


Vue.prototype.$axios = axios;
Vue.config.productionTip = false




new Vue({
  render: h => h(App), 
  router,
  store
}).$mount('#app')
