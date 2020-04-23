import Vue from 'vue';
import Router from 'vue-router';
import Personal from '@/components/Personal';
import Register from '@/components/Register';
import Login from '@/components/Login';
import Find from '@/components/FindPassword';
import Alter from '@/components/AlterPassword'


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
            path:'/alter',
            name:'Alter',
            component:Alter
        }
    ],
    mode:'history'
})
