<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>教学班管理</el-breadcrumb-item>
      <el-breadcrumb-item>教学班列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-table :data="classList" stripe border>
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column prop="id" label="教学班ID" width="100"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" width="200"></el-table-column>
        <el-table-column prop="teacherName" label="教师名称" width="200"></el-table-column>
        <el-table-column prop="classname" label="教学班名称" width="200"></el-table-column>
        <el-table-column prop="classroom" label="教室" width="150"></el-table-column>
        <el-table-column prop="time" label="上课时间" width="200"></el-table-column>
      </el-table>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pageNum"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="queryInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalCount">
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
      classList: [],
      totalCount: 0,
    };
  },
  created() {
    this.getClassList();
  },
  methods: {
    async getClassList() {
      const { data: res } = await this.$http.get("class/list", {
        params: this.queryInfo,
      });
      if (res.code == 200) {
        this.$message.success("获取教学班信息成功");
        this.classList = res.data.list.map(item => ({
          id: item.id,
          courseName: item.course.courseName, // 从course_name获取课程名称
          teacherName: item.teacher.name, // 从teacher_name获取教师名称
          classname: item.classname, // 获取教学班名称
          classroom: item.classroom, // 获取教室
          time: item.time // 获取上课时间
        }));
        this.totalCount = res.data.total;
      } else {
        this.$message.error("获取教学班信息失败");
      }
    },
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getClassList();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getClassList();
    },
  },
};
</script>

<style>
/* Your styles here */
</style>
