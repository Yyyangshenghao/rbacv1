import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login'
import Test1 from '../views/test'
import Department from '../views/department'
import Employee from '../views/employee'
import Permission from '../views/permission'
import Role from '../views/role'
import Main from '../views/main'
import NoPermission from '../views/nopermission'
import Welcome from '../views/welcome'
import Face from "@/views/face";
import Recognition from "@/views/recognition";
import Course from "@/views/course";
import Class from "@/views/class";
import Attendance from "@/views/attendance";
import Signin from "@/views/signin";
Vue.use(VueRouter)

const routes = [{
    path: "/",
    redirect: "/login"
  },
  {
    path: "/login",
    component: Login
  },
  {
    path: "/main",
    component: Main,
    redirect: "/welcome",
    children: [{
        path: "/welcome",
        component: Welcome
      },
      {
        path: "/department",
        component: Department
      },
      {
        path: "/employee",
        component: Employee
      },
      {
        path: "/permission",
        component: Permission
      },
      {
        path: "/role",
        component: Role
      },
      {
        path: "/nopermission",
        component: NoPermission
      },
      {
        path: "/test",
        component: Test1
      },
      {
        path: "/face",
        component: Face
      },
      {
        path: "/recognition",
        component: Recognition
      },
      {
        path: "/course",
        component: Course
      },
      {
        path: "/class",
        component: Class
      },
      {
        path: "/attendance",
        component: Attendance
      },
      {
        path: "/signin",
        component: Signin
      },
    ]
  },
]

const router = new VueRouter({
  routes
})

const originalPush = VueRouter.prototype.push
  VueRouter.prototype.push = function push(location) {
   return originalPush.call(this, location).catch(err => err)
}
// 挂载路由导航
router.beforeEach((to,from,next) =>{
	console.log("router---beforeEach")
	// to 将要访问的路径
	// from 代表从哪个路径跳转而来
	// next 是一个函数，表示放行
	//     next()  放行    next('/login')  强制跳转
	if(to.path==="/login") return next();
	const token=window.sessionStorage.getItem("userId");
	if(token) return next();
	next("/login")
});

export default router