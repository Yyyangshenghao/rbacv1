<template>
  <div class="sign-in">
    <!-- ...其他代码... -->
    <el-table :data="activeAttendances" style="width: 100%">
      <el-table-column prop="id" label="签到id"></el-table-column>
      <el-table-column prop="courseName" label="课程名"></el-table-column>
      <el-table-column prop="teacherName" label="教师名"></el-table-column>
      <el-table-column prop="status" label="签到状态"></el-table-column>
<!--      <el-table-column prop="start_time" label="开始时间"></el-table-column>-->
<!--      <el-table-column prop="end_time" label="结束时间"></el-table-column>-->
      <!-- 签到操作列 -->
      <el-table-column fixed="right" label="操作">
        <template v-slot="scope">
          <el-button type="primary" size="small" @click="handleSignIn(scope.row)">签到</el-button>
        </template>
      </el-table-column>
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
        const { data: res } = await this.$http.get(`/singin/listForStudent/${studentId}`);
        if (res.code === 200) {
          // 过滤状态为0的记录
          this.activeAttendances = res.data.filter(item => item.status === 0).map(item => ({
            ...item,
            status: '正在进行中' // 将状态 '0' 转换为文本
          }));
        } else {
          this.$message.error('获取签到信息失败');
        }
      } catch (error) {
        this.$message.error('请求签到信息时发生错误');
      }
    },
    handleSignIn(row) {
      // 使用 $router.push 来跳转到人脸识别页面
      // 传递参数，签到记录的ID
      this.$router.push({ path: '/recognition', query: { attendanceId: row.id } });
    },
  },
  created() {
    this.getActiveAttendances();
  }
};
</script>
