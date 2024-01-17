<template>
  <div class="publish-sign-in">
    <el-card class="box-card">
      <div slot="header" class="header">
        <span>发布课程签到</span>
      </div>

      <el-form :model="signInForm" label-position="top">
        <el-form-item label="选择课程" :label-width="formLabelWidth">
          <el-select v-model="signInForm.classId" filterable placeholder="请选择教学班">
            <el-option
                v-for="classItem in classList"
                :key="classItem.id"
                :label="classItem.classname"
                :value="classItem.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="开始时间" :label-width="formLabelWidth">
          <el-date-picker
              v-model="signInForm.start_time"
              type="datetime"
              placeholder="选择开始时间"
              :picker-options="startPickerOptions"
              value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="结束时间" :label-width="formLabelWidth">
          <el-date-picker
              v-model="signInForm.end_time"
              type="datetime"
              placeholder="选择结束时间"
              :picker-options="endPickerOptions"
              value-format="yyyy-MM-dd HH:mm:ss"
              :disabled="!signInForm.start_time">
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="publishSignIn">发布签到</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // ...其他数据属性
      signInForm: {
        classId: '', // 用于选择教学班的ID
        start_time: '',
        end_time: '',
      },
      classList: [], // 用于从后端获取教学班列表
      // ...其他数据属性
    };
  },
  computed: {
// 开始时间的选择器选项
    startPickerOptions() {
      return {
        disabledDate(time) {
// 禁止选择当前时间之前的时间
          return time.getTime() < Date.now() - 8.64e7; // 这里减去一天的时间是为了确保用户不能选择今天之前的日期
        },
      };
    },
// 结束时间的选择器选项
    endPickerOptions() {
      return {
        disabledDate: time => {
// 如果没有选择开始时间，则禁用所有日期
          if (!this.signInForm.start_time) {
            return true;
          }
// 禁止选择开始时间之前的时间
          return time.getTime() <= new Date(this.signInForm.start_time).getTime();
        },
      };
    },
  },
  methods: {
    // ...其他方法
    // 获取教学班列表
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
    // 发布签到
    async publishSignIn() {
      // 构建请求体，包含选择的教学班ID、开始时间和结束时间
      const requestBody = {
        classId: this.signInForm.classId,
        startTime: new Date(this.signInForm.start_time).toISOString(),
        endTime: new Date(this.signInForm.end_time).toISOString(),
      };
      try {
        const { data: res } = await this.$http.post('/attendance/publish', requestBody);
        if (res.code === 200) {
          this.$message.success('签到发布成功');
          // 可能需要做一些后续操作，比如清空表单或更新列表
        } else {
          this.$message.error('签到发布失败');
        }
      } catch (error) {
        this.$message.error('发布签到时发生错误');
      }
    },
    // ...其他方法
  },
  created: function () {
    this.getTeacherClasses(); // 在组件创建时获取教学班列表
  }
};
</script>

<style scoped>
.publish-sign-in .el-card {
  /* 与生成的UI设计样式相符的样式 */
}

.publish-sign-in .header {
  /* 与生成的UI设计样式相符的样式 */
}

.publish-sign-in .el-form-item {
  margin-bottom: 20px; /* 表单项之间的间距 */
}

.publish-sign-in .el-button {
  /* 与生成的UI设计样式相符的样式 */
}
</style>
