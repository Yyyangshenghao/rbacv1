<template>
  <div class="view-attendance-sessions">
    <el-card class="box-card">
      <div slot="header" class="header">
        <span>查看签到情况</span>
      </div>

      <el-form :model="attendanceForm" label-position="top">
        <el-form-item label="选择课程" :label-width="formLabelWidth">
          <el-select v-model="attendanceForm.classId" filterable placeholder="请选择教学班">
            <el-option
                v-for="classItem in classList"
                :key="classItem.id"
                :label="classItem.classname"
                :value="classItem.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="viewAttendanceSessions">查看签到列表</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="attendanceSessions" style="width: 100%">
        <el-table-column prop="id" label="签到号"></el-table-column>
        <el-table-column prop="status" label="签到状态"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="viewStudentAttendance(scope.row.id)">查看</el-button>
          </template>
        </el-table-column>
<!--        <el-table-column prop="startTime" label="开始时间"></el-table-column>-->
<!--        <el-table-column prop="endTime" label="截止时间"></el-table-column>-->
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
    <el-dialog
        title="学生签到详情"
        :visible.sync="showAttendanceDetailsDialog"
        width="50%">
      <div>
        <!-- 这里放置签到详情内容 -->
        <div class="two-columns">
          <div class="column">
            <h3>已签到的同学</h3>
            <ul>
              <!-- 这里只展示学生姓名，不展示状态 -->
              <li v-for="detail in signedStudents" :key="detail.id">
                {{ detail.name }}
              </li>
            </ul>
          </div>
          <div class="column">
            <h3>未签到的同学</h3>
            <ul>
              <!-- 这里也是只展示学生姓名 -->
              <li v-for="detail in unsignedStudents" :key="detail.id">
                {{ detail.name }}
              </li>
            </ul>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      attendanceForm: {
        classId: '',
      },
      classList: [],
      attendanceSessions: [],
      totalCount: 0, // 总条目数
      queryInfo: {   // 查询信息
        pageNum: 1,   // 当前页码
        pageSize: 10  // 每页条目数
      },
      showAttendanceDetailsDialog: false,
      currentAttendanceId: null,
      studentAttendanceDetails: [],
      signedStudents: [],
      unsignedStudents: [],
    };
  },
  methods: {
    async getTeacherClasses() {
      try {
        const teacherId = window.sessionStorage.getItem("userId");
        const { data: res } = await this.$http.get(`/class/listForTeacher/${teacherId}`);
        if (res.code === 200) {
          this.classList = res.data;
        } else {
          this.$message.error('获取教学班列表失败');
        }
      } catch (error) {
        this.$message.error('请求教学班列表时发生错误');
      }
    },
    async viewAttendanceSessions() {
      if (!this.attendanceForm.classId) {
        this.$message.error('请选择一个教学班');
        return;
      }

      try {
        const { data: res } = await this.$http.get(`/attendance/sessions/${this.attendanceForm.classId}`,{
          params: this.queryInfo
        });
        console.log(res)
        if (res.code == 200) {
          this.attendanceSessions = res.data.map(item => ({
            id: item.id,
            status: (item.status ? "已结束" : "正在进行中"),
            // startTime: item.start_time,
            // endTime: item.end_time,
          }));
          this.totalCount = res.data.total;
        } else {
          this.$message.error('获取签到列表失败');
        }
      } catch (error) {
        console.error('Error fetching attendance sessions:', error);
        this.$message.error('获取签到列表时发生错误');
      }
    },
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.viewAttendanceSessions();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.viewAttendanceSessions();
    },
    async viewStudentAttendance(attendanceId) {
      console.log("查看学生签到情况，签到ID：", attendanceId);
      this.currentAttendanceId = attendanceId;
      // 发起请求获取学生签到详情
      try {
        const {data: res} = await this.$http.get(`/attendance/details/${attendanceId}`);
        if (res.code === 200) {
          this.studentAttendanceDetails = res.data;
          this.signedStudents = this.studentAttendanceDetails.filter(student => student.status === 1);
          this.unsignedStudents = this.studentAttendanceDetails.filter(student => student.status === 0);

          this.showAttendanceDetailsDialog = true;
        } else {
          this.$message.error('获取学生签到详情失败');
        }
      } catch (error) {
        console.error('Error fetching attendance details:', error);
        this.$message.error('获取学生签到详情时发生错误');
      }
    },
  },

  created: function () {
    this.getTeacherClasses(); // 初始化时获取教学班列表
  }
};
</script>

<style scoped>
.view-attendance-sessions .el-card {
  /* 与生成的UI设计样式相符的样式 */
}

.view-attendance-sessions .header {
  /* 与生成的UI设计样式相符的样式 */
}

.view-attendance-sessions .el-form-item {
  margin-bottom: 20px; /* 表单项之间的间距 */
}

.view-attendance-sessions .el-button {
  /* 与生成的UI设计样式相符的样式 */
}
</style>
