package cn.wolfcode.rbac.web.controller;
import cn.wolfcode.rbac.domain.Department;
import cn.wolfcode.rbac.domain.query.QueryObject;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.domain.vo.RoleVo;
import cn.wolfcode.rbac.service.IDepartmentService;
import cn.wolfcode.rbac.service.IRoleService;
import cn.wolfcode.rbac.utils.RequirePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/role")
@CrossOrigin(allowCredentials="true")
public class RoleApiController {
    @Autowired
    private IRoleService roleService;

    @GetMapping("/listAll")
    @ResponseBody
    @RequirePermission({"角色列表","role:listAll"})
    public R listAll() {
        return R.ok(roleService.listAll());
    }

    @GetMapping("/list")
    @ResponseBody
    @RequirePermission({"角色分页列表","role:list"})
    public R list(QueryObject qo) {
        return R.ok(roleService.selectByPage(qo));
    }


    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @RequirePermission({"角色删除","role:delete"})
    public R delete(@PathVariable Long id) {
        roleService.deleteById(id);
        return R.ok("删除成功");
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    @RequirePermission({"角色信息","role:info"})
    public R info(@PathVariable Long id) {
        return R.ok(roleService.selectById(id));
    }

    @GetMapping("/query/{employeeId}")
    @ResponseBody
    @RequirePermission({"角色信息","role:query"})
    public R queryRoles(@PathVariable Long employeeId) {
        return R.ok(roleService.queryRolesByEmployeeId(employeeId));
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    @RequirePermission({"角色保存或更新","role:saveOrUpdate"})
    public R saveOrUpdate(@RequestBody RoleVo roleVo) {
        roleService.saveOrUpdate(roleVo);
        return R.ok("保存或者更新成功");
    }


}
