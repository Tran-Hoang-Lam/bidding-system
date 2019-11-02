import Vue from 'vue'
import Home from "./Home";
import VueRouter from 'vue-router'
import Contents from "./components/page/Contents";
import Bidder from "./components/page/Bidder";
import Auctioneer from "./components/page/Auctioneer";

Vue.config.productionTip = false;
Vue.use(VueRouter);

const router = new VueRouter({
  routes: [
    { path: '/', component: Contents },
    { path: '/bidder', component: Bidder },
    { path: '/auctioneer', component: Auctioneer }
  ]
});

new Vue({
  router,
  render: h => h(Home)
}).$mount('#app');
