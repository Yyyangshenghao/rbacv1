<template>
  <div class="sign-in">
    <el-card class="box-card">
      <div slot="header" class="header">
        <span>学生签到</span>
      </div>

      <el-form :model="signInForm" label-position="top">
        <el-form-item label="选择教学班" :label-width="formLabelWidth">
          <el-select v-model="signInForm.classId" filterable placeholder="请选择教学班">
            <el-option
                v-for="classItem in classList"
                :key="classItem.id"
                :label="classItem.classname"
                :value="classItem.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitSignIn">提交签到</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      signInForm: {
        classId: '', // 用于选择教学班的ID
      },
      classList: [], // 用于从后端获取教学班列表
      formLabelWidth: '100px',
    };
  },
  methods: {
    // 获取教学班列表
    async getClasses() {
      try {
        const { data: res } = await this.$http.get('/class/listAvailable');
        if (res.code === 200) {
          this.classList = res.data;
        } else {
          this.$message.error('获取教学班列表失败');
        }
      } catch (error) {
        this.$message.error('请求教学班列表时发生错误');
      }
    },
    // 提交签到
    async submitSignIn() {
      if (!this.signInForm.classId) {
        this.$message.warning('请选择教学班');
        return;
      }
      try {
        const requestBody = {
          classId: this.signInForm.classId,
          // 此处可以根据实际需要添加更多的签到信息，例如学生ID等
        };
        const { data: res } = await this.$http.post('/attendance/signIn', requestBody);
        if (res.code === 200) {
          this.$message.success('签到成功');
        } else {
          this.$message.error('签到失败');
        }
      } catch (error) {
        this.$message.error('签到请求发生错误');
      }
    },
  },
  created() {
    this.getClasses();
  }
};
</script>

<style>
.sign-in .el-card {
  /* 样式保持一致性 */
}

.sign-in .header {
  /* 样式保持一致性 */
}

.sign-in .el-form-item {
  margin-bottom: 20px; /* 适当的表单项间距 */
}

.sign-in .el-button {
  /* 样式保持一致性 */
}
</style>