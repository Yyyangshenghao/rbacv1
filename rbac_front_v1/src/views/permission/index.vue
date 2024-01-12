<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>权限列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-button type="primary" @click="load"
        >重新加载</el-button
      >
      <el-table :data="permissionlist" stripe border>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="id" label="权限标识"></el-table-column>
        <el-table-column prop="name" label="权限名称"></el-table-column>
        <el-table-column prop="expression" label="权限表达式"></el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="[1, 2, 5, 10]"
        :page-size="queryInfo.pageSize"
        layout="total,sizes,prev,pager,next,jumper"
        :total="totalCount"
      >
      </el-pagination>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      queryInfo: {
        pageNum: 1,
        pageSize: 10,
      },
      permissionlist: [],
      totalCount: 0
    };
  },
  created: function () {
    this.getPermissionList();
  },
  methods: {
    async getPermissionList() {
      const { data: res } = await this.$http.get("permission/list", {
        params: this.queryInfo,
      });
      if (res.code == 200) {
        this.$message.success("获取权限信息成功");
        this.permissionlist = res.data.list;
        this.totalCount = res.data.total;
      } else {
        this.$message.error("获取权限信息失败");
      }
    },
    // 分页数据处理
    handleSizeChange: function (pageSize) {
      this.queryInfo.pageSize = pageSize;
      this.getPermissionList();
    },
    handleCurrentChange: function (currentPage) {
      this.queryInfo.pageNum = currentPage;
      this.getPermissionList();
    },
    // 加载权限
    async load(){
      const { data: res } = await this.$http.post("permission/load");
      if (res.code == 200) {
        this.getPermissionList()
      } 
    }
  },
};
</script>

<style>
</style>