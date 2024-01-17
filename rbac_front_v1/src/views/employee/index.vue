  <template>
    <div>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户列表</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card class="box-card">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input placeholder="输入姓名/邮箱" v-model="queryInfo.keyword">
            </el-input>
          </el-col>
          <el-col :span="8">
            <el-select placeholder="班级" v-model="queryInfo.deptId">
              <el-option
                  v-for="department in departmentlist"
                  :key="department.id"
                  :label="department.name"
                  :value="department.id"
              >
              </el-option>
            </el-select>
            <el-button type="primary" @click="doQuery">查询</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="dialogFormVisible = true"
            >添加用户</el-button
            >
          </el-col>
        </el-row>
        <el-table :data="employeelist" stripe border>
          <el-table-column type="index"></el-table-column>
          <el-table-column
              prop="id"
              label="用户标识"
              width="150px"
          ></el-table-column>
          <el-table-column
              prop="name"
              label="用户名称"
              width="150px"
          ></el-table-column>
          <el-table-column
              prop="email"
              label="邮箱"
              width="150px"
          ></el-table-column>
          <el-table-column
              prop="age"
              label="年龄"
              width="150px"
          ></el-table-column>
          <el-table-column label="超级管理员" width="150px">
            <template slot-scope="scope">
              <el-switch
                  v-model="scope.row.admin"
                  @change="employeeStateChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column
              prop="department.name"
              label="班级名称"
              width="150px"
          ></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-tooltip
                  class="item"
                  effect="dark"
                  content="编辑用户"
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
                  content="删除用户"
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
          title="添加用户"
          :visible.sync="dialogFormVisible"
          @close="addDialogClosed"
      >
        <el-form :model="addForm" ref="addFormRefs" label-width="70px">
          <el-form-item label="名称" label-width="120px" prop="name">
            <el-input v-model="addForm.name"></el-input>
          </el-form-item>
          <el-form-item label="密码" label-width="120px" prop="password">
            <el-input v-model="addForm.password"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" label-width="120px" prop="email">
            <el-input v-model="addForm.email"></el-input>
          </el-form-item>
          <el-form-item label="年龄" label-width="120px" prop="age">
            <el-input v-model="addForm.age"></el-input>
          </el-form-item>
          <el-form-item label="班级" label-width="120px" prop="department.id">
            <el-select placeholder="班级" v-model="addForm.department.id">
              <el-option
                  v-for="department in departmentlist"
                  :key="department.id"
                  :label="department.name"
                  :value="department.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="超级管理员" label-width="120px" prop="admin">
            <el-switch v-model="addForm.admin"></el-switch>
          </el-form-item>
          <el-form-item
              label="分配角色"
              label-width="100px"
              v-if="!addForm.admin"
          >
            <el-transfer
                v-model="addForm.roleIds"
                :data="roleData"
                :titles="['未分配', '已分配']"
            >
            </el-transfer>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="addEmployee">确定</el-button>
        </div>
      </el-dialog>

      <el-dialog
          title="编辑用户"
          :visible.sync="editDialogVisible"
          @close="editDialogClosed"
      >
        <el-form :model="editForm" ref="editFormRefs" label-width="70px">
          <el-form-item label="名称" label-width="120px" prop="name">
            <el-input v-model="editForm.name"></el-input>
          </el-form-item>
          <el-form-item label="密码" label-width="120px" prop="password">
            <el-input v-model="editForm.password"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" label-width="120px" prop="email">
            <el-input v-model="editForm.email"></el-input>
          </el-form-item>
          <el-form-item label="年龄" label-width="120px" prop="age">
            <el-input v-model="editForm.age"></el-input>
          </el-form-item>
          <el-form-item label="班级" label-width="120px" prop="department.id">
            <el-select placeholder="班级" v-model="editForm.department.id">
              <el-option
                  v-for="department in departmentlist"
                  :key="department.id"
                  :label="department.name"
                  :value="department.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="超级管理员" label-width="120px" prop="admin">
            <el-switch v-model="editForm.admin"></el-switch>
          </el-form-item>
          <el-form-item
              label="分配角色"
              label-width="100px"
              v-if="!editForm.admin"
          >
            <el-transfer
                v-model="editRoleIds"
                :data="roleData"
                :titles="['未分配', '已分配']"
            ></el-transfer>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="editEmployee">确定</el-button>
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
          deptId: -1,
          keyword: "",
        },
        employeelist: [],
        departmentlist: [],
        rolelist: [],
        roleData: [],
        totalCount: 0,
        addForm: {
          name: "",
          password: "",
          email: "",
          age: "",
          department: {
            id: 1,
          },
          admin: true,
          roleIds:[]
        },
        dialogFormVisible: false,
        editForm: {
          id: "",
          name: "",
          password: "",
          email: "",
          age: "",
          department: {
            id: 1,
          },
          admin: true,
        },
        editRoleIds:[],
        editDialogVisible: false,
      };
    },
    created: function () {
      this.getEmployeeList();
      this.getDepartmentList();
      this.getRoleList();
    },
    methods: {
      // 获取角色列表信息
      async getRoleList() {
        const { data: res } = await this.$http.get("role/listAll");
        if (res.code == 200) {
          this.rolelist = res.data;
          // 处理分配角色数据
          this.rolelist.forEach((role) => {
            this.roleData.push({
              key: role.id,
              label: role.name,
            });
          });
        }
      },
      // 获取班级列表信息
      async getDepartmentList() {
        const { data: res } = await this.$http.get("department/listAll");
        if (res.code == 200) {
          this.departmentlist = res.data;
          this.departmentlist.unshift({
            id: -1,
            name: "全部",
            sn: "all",
          });
        } else {
          this.$message.error("获取班级信息失败");
        }
      },
      // 获取用户列表新
      async getEmployeeList() {
        const { data: res } = await this.$http.get("employee/list", {
          params: this.queryInfo,
        });
        if (res.code == 200) {
          this.$message.success("获取用户信息成功");
          this.employeelist = res.data.list;
          this.totalCount = res.data.total;
        } else {
          this.$message.error("获取用户信息失败");
        }
      },
      async doQuery() {
        this.getEmployeeList();
      },
      handleSizeChange: function (pageSize) {
        this.queryInfo.pageSize = pageSize;
        this.getEmployeeList();
      },
      handleCurrentChange: function (currentPage) {
        this.queryInfo.pageNum = currentPage;
        this.getEmployeeList();
      },
      //
      async employeeStateChange(employee) {
        // 更新管理状态
        let adminStateVo = {
          id:employee.id,
          admin:employee.admin
        };
        const { data: res } = await this.$http.post(
            "employee/updateState",
            adminStateVo
        );
        if (res.code != 200) {
          this.$message.error("修改更新用户状态失败");
          employee.admin = !employee.admin;
        } else {
          this.$message.success("修改用户信息成功");
          this.getEmployeeList();
        }
      },
      addDialogClosed: function () {
        this.$refs.addFormRefs.resetFields();
        this.addForm.roleIds=[];
      },
      // 添加用户
      async addEmployee() {
        let employeeRoleVo = {
          employee: this.addForm,
          roleIds: this.addForm.roleIds,
        };
        const { data: res } = await this.$http.post(
            "employee/saveOrUpdate",
            employeeRoleVo
        );
        if (res.code != 200) {
          this.$message.error("添加用户失败");
        } else {
          this.$message.success("添加用户成功");
          this.getEmployeeList();
        }
        this.dialogFormVisible = false;
      },
      editDialogClosed() {
        this.$refs.editFormRefs.resetFields();
        this.editRoleIds=[];
      },
      showEditDialog(id) {
        // 获取用户信息
        this.getEmployeeById(id);
        // 获取角色信息
        this.getRolesByEmpId(id);
      },
      async getEmployeeById(id){
        const { data: res } = await this.$http.get("employee/info/" + id);
        if (res.code != 200) {
          this.$message.error("查询用户信息失败");
        } else {
          this.editDialogVisible = true;
          this.editForm = res.data;
        }
      },
      async getRolesByEmpId(id){
        const { data: res } = await this.$http.get("role/query/" + id);
        if (res.code != 200) {
          this.$message.error("查询角色信息失败");
        }else{
          if (res.data != null && res.data.length !=0 ) {
            res.data.forEach(role=>{
              this.editRoleIds.push(role.id);
            });
          }
        }
      },
      // 编辑员信息
      async editEmployee() {
        let employeeRoleVo = {
          employee: this.editForm,
          roleIds: this.editRoleIds
        };
        const { data: res } = await this.$http.post(
            "employee/saveOrUpdate",
            employeeRoleVo
        );
        if (res.code != 200) {
          this.$message.error("修改用户信息失败");
        } else {
          this.$message.success("修改用户信息成功");
          this.getEmployeeList();
        }
        this.editDialogVisible = false;
      },
      // 删除用户信息
      async deleteDialog(id) {
        const result = await this.$confirm(
            "此操作将永久删除该用户信息, 是否继续?",
            "提示",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }
        ).catch((e) => e);
        if (result == "cancel") {
          this.$message.info("取消删除");
        }
        if (result == "confirm") {
          const { data: res } = await this.$http.delete("employee/delete/" + id);
          if (res.code != 200) {
            this.$message.error("删除用户信息失败");
          } else {
            this.$message.success("删除用户信息成功");
            this.getEmployeeList();
          }
        }
      },
    }
  };
  </script>
  <style>
  </style>