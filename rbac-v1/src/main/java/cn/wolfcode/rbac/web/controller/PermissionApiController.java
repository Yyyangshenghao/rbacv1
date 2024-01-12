package cn.wolfcode.rbac.web.controller;
import cn.wolfcode.rbac.domain.Department;
import cn.wolfcode.rbac.domain.query.QueryObject;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.service.IDepartmentService;
import cn.wolfcode.rbac.service.IPermissionService;
import cn.wolfcode.rbac.utils.RequirePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/permission")
@CrossOrigin(allowCredentials="true")
public class PermissionApiController {
    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/listAll")
    @ResponseBody
    @RequirePermission({"权限列表","permission:listAll"})
    public R listAll() {
        return R.ok(permissionService.listAll());
    }

    @GetMapping("/list")
    @ResponseBody
    @RequirePermission({"权限分页列表","permission:list"})
    public R list(QueryObject qo) {
        return R.ok(permissionService.query(qo));
    }


    /**
     * 加载权限
     * @return
     */
    @PostMapping("/load")
    @ResponseBody
    @RequirePermission({"加载权限","permission:load"})
    public R load() {
        permissionService.load();
        return R.ok("加载权限信息成功");
    }

    @GetMapping("/queryPermission/{roleId}")
    @ResponseBody
    @RequirePermission({"查询权限","department:queryPermission"})
    public R queryPermission(@PathVariable long roleId) {
        return R.ok(permissionService.queryByRoleId(roleId));
    }

}
