<template>
  <el-container class="home-container">
    <el-header style="display: flex; align-items: center;">
      <img src="../../assets/logo.png" class="logo" />
      <img src="../../assets/logo1.png" class="logo1" />
      <div style="display: flex; align-items: center; margin-left: auto;">
        <span>{{ username }}</span>
        <el-button type="info" @click="logout" style="margin-left: 10px;">退出</el-button>
      </div>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle-button" @click="toggleMenu">|||</div>
        <el-menu
          unique-opened
          background-color="#333744"
          text-color="#fff"
          :collapse="isCollapse"
          :collapse-transition="false"
          router
        >
          <el-submenu
            :index="item.id + ''"
            v-for="(item, index) in menulist"
            :key="item.id"
          >
            <template slot="title">
              <i :class="objicons[index]"></i>
              <span>{{ item.name }}</span>
            </template>
            <el-menu-item
                :index="subitem.path"
                :key="subitem.id"
                v-for="subitem in item.children"
            >
              <i :class="`el-icon-${subitem.icon}`"></i>
              <span slot="title">{{ subitem.name }}</span>
            </el-menu-item>

          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
          <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      username:window.sessionStorage.getItem("userName"),
      menulist: [
        {
          id: 1,
          name: "教务处",
          children: [
            { id: 8, name: "课程管理", path: "/course", icon: "s-management" },
            { id: 9, name: "教学班管理", path: "/class", icon: "s-custom"},
            { id: 10, name: "班级管理", path: "/department", icon: "s-cooperation" },
            { id: 11, name: "用户管理", path: "/employee",icon: "s-platform" },
            { id: 12, name: "角色管理", path: "/role", icon: "s-promotion" },
            { id: 13, name: "权限管理", path: "/permission", icon: "s-operation" },
          ],
        },
        {
          id: 2,
          name: "教师",
          children: [
            { id: 14, name: "签到发布", path: "/attendance",icon: "edit-outline"},
            { id: 15, name: "签到情况", path: "/attresult",icon:"document"},
          ],
        },
        {
          id: 3,
          name: "学生",
          children: [
            { id: 16, name: "人脸上传", path: "/face", icon:"upload2" },
            // { id: 17, name: "人脸识别", path: "/recognition" },
            { id: 18, name: "签到", path: "/signin", icon:"check"},
          ],
        },
      ],

      objicons: [
        "iconfont icon-users",
        "iconfont icon-tijikongjian",
        "iconfont icon-shangpin",
      ],
      isCollapse: false,
    };
  },
  methods: {
    toggleMenu: function () {
      this.isCollapse = !this.isCollapse;
    },
    async logout(){
      // 发送请求退出
      const { data: res } = await this.$http.get("logout");
      if (res.code !== 200) {
        console.log("退出失败");
      } else {
        console.log("退出成功");
        sessionStorage.clear();
        this.$router.push("/login");
      }
    }
  },
};
</script>
<style>
@import "../../assets/css/home/home.css";
</style>