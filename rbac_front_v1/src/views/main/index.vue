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
              :index="subitem.path + ''"
              :key="subitem.id + ''"
              v-for="subitem in item.children"
            >
              <i class="el-icon-menu"></i>
              <span>{{ subitem.name }}</span>
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
          name: "系统管理",
          children: [
            {
              id: 10,
              name: "部门管理",
              path: "/department"
            },
            {
              id: 11,
              name: "员工管理",
              path: "/employee"
            },
            {
              id: 12,
              name: "角色管理",
              path: "/role"
            },
            {
              id: 13,
              name: "权限管理",
              path: '/permission'
            },
            {
              id: 14,
              name: "人脸上传",
              path: '/face'
            },
            {
              id: 15,
              name: "人脸识别测试",
              path: '/recognition'
            },

          ],
        },
      ],
      objicons: [
        "iconfont icon-users",
        "iconfont icon-tijikongjian",
        "iconfont icon-shangpin",
        "iconfont icon-danju",
        "iconfont icon-baobiao",
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
      if (res.code != 200) {
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