<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>教学班管理</el-breadcrumb-item>
      <el-breadcrumb-item>教学班列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="输入教学班名称" v-model="queryInfo.keyword">
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="doQuery">查询</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="dialogFormVisible = true">添加教学班</el-button>
        </el-col>
      </el-row>
      <el-table :data="classList" stripe border>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="id" label="教学班ID" width="100"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" width="200"></el-table-column>
        <el-table-column prop="teacherName" label="教师名称" width="100"></el-table-column>
        <el-table-column prop="classname" label="教学班名称" width="200"></el-table-column>
        <el-table-column prop="classroom" label="教室" width="150"></el-table-column>
        <el-table-column prop="time" label="上课时间" width="200"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="编辑教学班" placement="top">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除教学班" placement="top">
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
    <!-- Add/Edit Dialogs here (similar to the course management code) -->
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
