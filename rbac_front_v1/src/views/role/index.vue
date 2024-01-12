<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-button type="primary" @click="dialogFormVisible = true">添加角色</el-button>
      <el-table :data="roleList" stripe border>
        <el-table-column type="index"></el-table-column>
        <el-table-column
          prop="id"
          label="角色标识"
        ></el-table-column>
        <el-table-column
          prop="name"
          label="角色名称"
        ></el-table-column>
        <el-table-column
          prop="sn"
          label="角色简称"
        ></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-tooltip
              class="item"
              effect="dark"
              content="编辑角色"
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
              content="删除角色"
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
      title="添加角色"
      :visible.sync="dialogFormVisible"
      @close="addDialogClosed"
    >
      <el-form :model="addForm" ref="addFormRefs" label-width="70px">
        <el-form-item label="角色名称" label-width="120px" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="角色编号" label-width="120px" prop="sn">
          <el-input v-model="addForm.sn"></el-input>
        </el-form-item>
        <el-form-item
          label="分配权限"
          label-width="100px"
        >
          <el-transfer
            v-model="addForm.permissionIds"
            :data="permissionData"
            :titles="['未分配', '已分配']"
          >
          </el-transfer>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addRole">确定</el-button>
      </div>
    </el-dialog>
    
    <el-dialog
      title="编辑角色"
      :visible.sync="editDialogVisible"
      @close="editDialogClosed"
    >
      <el-form :model="editForm" ref="editFormRefs" label-width="70px">
        <el-form-item label="角色名称" label-width="120px" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="角色编号" label-width="120px" prop="sn">
          <el-input v-model="editForm.sn"></el-input>
        </el-form-item>
        <el-form-item
          label="分配权限"
          label-width="100px"
        >
        <el-transfer
            v-model="editPermissionIds"
            :data="permissionData"
            :titles="['未分配', '已分配']"
          ></el-transfer>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="editRole">确定</el-button>
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
      addForm:{
        name:"",
        sn:"",
        permissionIds:[]
      },
      editForm:{
        id:0,
        name:"",
        sn:""
      },
      permissionData:[],
      editPermissionIds:[],
      roleList: [],
      totalCount: 0,
      dialogFormVisible:false,
      editDialogVisible:false,
    };
  },
  created: function () {
    this.getRoleList();
    this.getAllPermissionList();
  },
  methods: {
    // 获取角色列表
    async getRoleList() {
      const { data: res } = await this.$http.get("role/list", {
        params: this.queryInfo,
      });
      if (res.code == 200) {
        this.$message.success("获取角色信息成功")
        this.roleList = res.data.list;
        this.totalCount = res.data.total;
      } else {
        this.$message.error("获取角色信息失败");
      }
    },
    // 获取所有权限列表
    async getAllPermissionList() {
      const { data: res } = await this.$http.get("permission/listAll");
      if (res.code == 200) {
        // 处理分配权限信息数据
        res.data.forEach((permission) => {
          this.permissionData.push({
            key: permission.id,
            label: permission.name,
          });
        });
      }
    },
    handleSizeChange: function (pageSize) {
      this.queryInfo.pageSize = pageSize;
      this.getRoleList();
    },
    handleCurrentChange: function (currentPage) {
      this.queryInfo.pageNum = currentPage;
      this.getRoleList();
    },
    addDialogClosed: function () {
      this.$refs.addFormRefs.resetFields();
      this.addForm.permissionIds=[];
    },
    async addRole(){
      let roleVo = {
        role: this.addForm,
        permissionIds: this.addForm.permissionIds,
      };
      const { data: res } = await this.$http.post(
        "role/saveOrUpdate",
        roleVo
      );
      if (res.code != 200) {
        this.$message.error("添加角色失败");
      } else {
        this.$message.success("添加角色成功");
        this.getRoleList();
      }
      this.dialogFormVisible = false;
    },
    editDialogClosed() {
      this.$refs.editFormRefs.resetFields();
      this.permissionIds=[];
    },
    showEditDialog(id) {
      this.getRoleById(id);
      this.getPermissionsById(id);
    },
    async getRoleById(id){
      const { data: res } = await this.$http.get("role/info/" + id);
      if (res.code != 200) {
        this.$message.error("查询角色信息失败");
      } else {
        this.editForm=res.data;
        this.editDialogVisible = true;
      }
    },
    async getPermissionsById(id){
      const { data: res } = await this.$http.get("permission/queryPermission/" + id);
      if (res.code != 200) {
        this.$message.error("查询权限信息失败");
      } else {
        this.editPermissionIds=res.data.map((permission) => {
          return permission.id;
        })
        this.editDialogVisible = true;
      }
    },
    async editRole(){
      let roleVo = {
        role: this.editForm,
        permissionIds: this.editPermissionIds
      };
      const { data: res } = await this.$http.post(
        "role/saveOrUpdate",
        roleVo
      );
      if (res.code != 200) {
        this.$message.error("修改角色信息失败");
      } else {
        this.$message.success("修改角色信息成功");
        this.getRoleList();
      }
      this.editDialogVisible = false;
    },
    // 删除员工信息
    async deleteDialog(id){
        const result=await this.$confirm('此操作将永久删除该角色信息, 是否继续?','提示',{
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).catch(e=>e);
        if(result=="cancel"){
            this.$message.info("取消删除");
        }
        if(result=="confirm"){
            const {data:res}=await this.$http.delete("role/delete/"+id);
            if(res.code!=200){
                this.$message.error("删除员工信息失败");
            }else{
                this.$message.success("删除员工信息成功");
                this.getRoleList();
            }
        }
    }
  },
};
</script>
<style>
</style>