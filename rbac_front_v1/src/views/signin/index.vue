<template>
  <div class="sign-in">
    <!-- ...其他代码... -->
    <el-table :data="activeAttendances" style="width: 100%">
      <el-table-column prop="courseName" label="课程名"></el-table-column>
      <el-table-column prop="teacherName" label="教师名"></el-table-column>
      <el-table-column prop="start_time" label="开始时间"></el-table-column>
      <el-table-column prop="end_time" label="结束时间"></el-table-column>
    </el-table>
    <!-- ...其他代码... -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      // ...其他数据...
      activeAttendances: [], // 存储正在进行中的签到信息
    };
  },
  methods: {
    // ...其他方法...
    // 获取学生相关的签到信息
    async getActiveAttendances() {
      const studentId = sessionStorage.getItem("userId")// 获取学生ID的逻辑;
      try {
        const { data: res } = await this.$http.get(`/attendance/listForStudent/${studentId}`);
        if (res.code === 200) {
          // 过滤状态为0的记录
          this.activeAttendances = res.data.filter(item => item.status === 0);
        } else {
          this.$message.error('获取签到信息失败');
        }
      } catch (error) {
        this.$message.error('请求签到信息时发生错误');
      }
    },
  },
  created() {
    this.getActiveAttendances();
  }
};
</script>
