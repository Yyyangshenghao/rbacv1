<template>
  <div class="view-student-attendance">
    <el-card class="box-card">
      <div slot="header" class="header">
        <span>查看学生签到情况</span>
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

        <el-form-item label="选择日期" :label-width="formLabelWidth">
          <el-date-picker
              v-model="attendanceForm.date"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="viewAttendance">查看签到</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="attendanceRecords" style="width: 100%">
        <el-table-column prop="studentName" label="学生姓名"></el-table-column>
        <el-table-column prop="attendanceStatus" label="签到状态"></el-table-column>
        <el-table-column prop="signInTime" label="签到时间"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      attendanceForm: {
        classId: '',
        date: '',
      },
      classList: [],
      attendanceRecords: [],
    };
  },
  methods: {
    async getClassList() {
      // 方法实现：获取教学班列表
    },
    async viewAttendance() {
      // 方法实现：查看学生签到情况
      // 使用attendanceForm.classId和attendanceForm.date来获取签到数据
    },
  },
  created() {
    this.getClassList(); // 初始化时获取教学班列表
  }
};
</script>

<style scoped>
.view-student-attendance .el-card {
  /* 与生成的UI设计样式相符的样式 */
}

.view-student-attendance .header {
  /* 与生成的UI设计样式相符的样式 */
}

.view-student-attendance .el-form-item {
  margin-bottom: 20px; /* 表单项之间的间距 */
}

.view-student-attendance .el-button {
  /* 与生成的UI设计样式相符的样式 */
}
</style>
