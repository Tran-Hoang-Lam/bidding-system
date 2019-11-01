import Vue from 'vue'
import Home from "./Home";
import VueRouter from 'vue-router'
import Contents from "./components/Contents";

Vue.config.productionTip = false;
Vue.use(VueRouter);

const router = new VueRouter({
  routes: [
    { path: '/', component: Contents }
  ]
});

new Vue({
  router,
  render: h => h(Home)
}).$mount('#app');
