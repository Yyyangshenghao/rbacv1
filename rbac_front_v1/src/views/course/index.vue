<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>课程管理</el-breadcrumb-item>
      <el-breadcrumb-item>课程列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="输入课程名" v-model="queryInfo.keyword">
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="doQuery">查询</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="dialogFormVisible = true">添加课程</el-button>
        </el-col>
      </el-row>
      <el-table :data="courselist" stripe border>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="id" label="课程ID" width="150px"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" width="150px"></el-table-column>
        <el-table-column prop="credit" label="学分" width="100px"></el-table-column>
        <el-table-column prop="hour" label="学时" width="100px"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="编辑课程" placement="top">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除课程" placement="top">
              <el-button type="warning" icon="el-icon-delete" size="mini" @click="deleteDialog(scope.row.id)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
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
    <el-dialog title="添加课程" :visible.sync="dialogFormVisible" @close="addDialogClosed">
      <el-form :model="addForm" ref="addFormRefs" label-width="70px">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input v-model.number="addForm.credit" type="number"></el-input>
        </el-form-item>
        <el-form-item label="学时" prop="hour">
          <el-input v-model.number="addForm.hour" type="number"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addCourse">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog
        title="编辑课程"
        :visible.sync="editDialogVisible"
        @close="editDialogClosed"
    >
      <el-form :model="editForm" ref="editFormRefs" label-width="70px">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input v-model.number="editForm.credit" type="number"></el-input>
        </el-form-item>
        <el-form-item label="学时" prop="hour">
          <el-input v-model.number="editForm.hour" type="number"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="editCourse">更新</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryInfo: {
        pageNum: 1,
        pageSize: 10,
        keyword: "",
      },
      courselist: [],
      totalCount: 0,
      addForm: {
        name: "",
        credit: null,
        hour: null,
      },
      editForm: {
        id: null,
        name: "",
        credit: null,
        hour: null,
      },
      dialogFormVisible: false,
      editDialogVisible: false,
    };
  },
  created() {
    this.getCourseList();
  },
  methods: {
    async getCourseList() {
      const { data: res } = await this.$http.get("course/list", {
        params: this.queryInfo,
      });
      if (res.code == 200) {
        this.$message.success("获取课程信息成功");
        this.courselist = res.data.list;
        this.totalCount = res.data.total;
      } else {
        this.$message.error("获取课程信息失败");
      }
    },
    async doQuery() {
// Perform the query based on the user's input
      this.getCourseList();
    },
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getCourseList();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getCourseList();
    },
    addDialogClosed() {
      this.$refs.addFormRefs.resetFields();
    },
    async addCourse() {
// Call your API to add a course
    },
    showEditDialog(course) {
      this.editForm = { ...course };
      this.editDialogVisible = true;
    },
    editDialogClosed() {
      this.$refs.editFormRefs.resetFields();
    },
    async editCourse() {
// Call your API to update the course
    },
    async deleteDialog(id) {
// Call your API to delete the course
    },
  }
};
</script>

<style>
</style>
