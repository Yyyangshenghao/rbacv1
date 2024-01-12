<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>部门管理</el-breadcrumb-item>
      <el-breadcrumb-item>部门列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-button type="primary" @click="dialogFormVisible = true"
        >添加部门</el-button
      >
      <el-table :data="departmentlist" stripe border>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="id" label="部门标识"></el-table-column>
        <el-table-column prop="name" label="部门名称"></el-table-column>
        <el-table-column prop="sn" label="部门编号"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-tooltip
              class="item"
              effect="dark"
              content="编辑部门"
              placement="top"
            >
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="showEditDialog(scope.row.id)"
              ></el-button>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="dark"
              content="删除部门"
              placement="top"
            >
              <el-button
                type="warning"
                icon="el-icon-delete"
                size="mini"
                @click="deleteDialog(scope.row.id)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
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
    <el-dialog
      title="添加部门"
      :visible.sync="dialogFormVisible"
      @close="addDialogClosed"
    >
      <el-form :model="addForm" ref="addFormRefs" label-width="70px">
        <el-form-item label="部门名称" label-width="120px" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="部门编号" label-width="120px" prop="sn">
          <el-input v-model="addForm.sn"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addDepartment">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="修改部门"
      :visible.sync="editDialogVisible"
      @close="editDialogClosed"
    >
      <el-form :model="editForm" ref="editFormRefs" label-width="70px">
        <el-form-item label="部门名称" label-width="120px" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="部门编号" label-width="120px" prop="sn">
          <el-input v-model="editForm.sn"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="editDepartment">确定</el-button>
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
      },
      departmentlist: [],
      totalCount: 0,
      addForm: {
        name: "",
        sn: "",
      },
      dialogFormVisible: false,
      editDialogVisible: false,
      editForm: {
        id: 0,
        name: "",
        sn: "",
      },
    };
  },
  created: function () {
    this.getDepartmentList();
  },
  methods: {
    async getDepartmentList() {
      const { data: res } = await this.$http.get("department/list", {
        params: this.queryInfo,
      });
      console.log(res);
      if (res.code == 200) {
        this.$message.success("获取部门信息成功");
        this.departmentlist = res.data.list;
        this.totalCount = res.data.total;
        console.log(this.departmentlist);
      } else {
        this.$message.error("获取部门信息失败");
      }
    },
    handleSizeChange: function (pageSize) {
      this.queryInfo.pageSize = pageSize;
      this.getDepartmentList();
    },
    handleCurrentChange: function (currentPage) {
      this.queryInfo.pageNum = currentPage;
      this.getDepartmentList();
    },
    addDialogClosed: function () {
      this.$refs.addFormRefs.resetFields();
    },
    async addDepartment() {
      const { data: res } = await this.$http.post(
        "department/saveOrUpdate",
        this.addForm
      );
      console.log(res);
      if (res.code != 200) {
        this.$message.error("添加部门失败");
      } else {
        this.$message.success("添加部门成功");
        this.getDepartmentList();
      }
      this.dialogFormVisible = false;
    },
    editDialogClosed() {
      this.$refs.editFormRefs.resetFields();
    },
    async showEditDialog(id) {
      console.log(id);
      const { data: res } = await this.$http.get("department/info/" + id);
      if (res.code != 200) {
        this.$message.error("查询部门信息失败");
      } else {
        this.editDialogVisible = true;
        this.editForm = res.data;
      }
      console.log(this.editForm);
    },
    async editDepartment() {
      const { data: res } = await this.$http.post("department/saveOrUpdate",this.editForm);
      if (res.code != 200) {
        this.$message.error("修改部门信息失败");
      } else {
        this.$message.success("修改部门信息成功");
        this.getDepartmentList();
      }
      this.editDialogVisible=false;	
    },
    async deleteDialog(id){
        const result=await this.$confirm('此操作将永久删除该部门信息, 是否继续?','提示',{
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).catch(e=>e);
        console.log(result);
        if(result=="cancel"){
            this.$message.info("取消删除");
        }
        if(result=="confirm"){
            const {data:res}=await this.$http.delete("department/delete/"+id);
            if(res.code!=200){
                this.$message.error("删除部门信息失败");
            }else{
                this.$message.success("删除部门信息成功");
                this.getDepartmentList();
            }
        }
    }
  },
};
</script>

<style>
</style>