import Vue from 'vue';
import Router from 'vue-router';
import Personal from '@/components/Personal';
import Register from '@/components/Register';
import Login from '@/components/Login';
import Find from '@/components/FindPassword';
import Test1 from '@/Test1.vue';
import Test2 from '@/Test2.vue';
import Test3 from '@/Test3.vue';
import HomePage from '@/HomePage';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path:'/personal',
            name:'Personal',
            component: Personal
        },
        {
            path:'/',
            name:'Login',
            component:Login
        },
        {
            path:'/register',
            name:'Register',
            component:Register
        },
        {
            path:'/find',
            name:'Find',
            component:Find
        },
        {
            path:'/test1',
            name:'Test1',
            component:Test1
        },
        {
            path:'/test2',
            name:'Test2',
            component:Test2
        },
        {
            path:'/test3',
            name:'Test3',
            component:Test3
        },
        {
            path:'/homepage',
            name:'HomePage',
            component:HomePage
        }
    ],
    // mode:'history'
})
