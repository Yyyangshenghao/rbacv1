package cn.wolfcode.rbac.web.controller;
import cn.wolfcode.rbac.domain.Department;
import cn.wolfcode.rbac.domain.query.QueryObject;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.service.IDepartmentService;
import cn.wolfcode.rbac.utils.RequirePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/department")
@CrossOrigin(allowCredentials="true")
public class DepartmentApiController {
    @Autowired
    private IDepartmentService departmentService;

    @GetMapping("/listAll")
    @ResponseBody
    @RequirePermission({"部门列表","department:listAll"})
    public R listAll() {
        return R.ok(departmentService.selectAll());
    }

    @GetMapping("/list")
    @ResponseBody
    @RequirePermission({"部门分页列表","department:list"})
    public R list(QueryObject qo) {
        return R.ok(departmentService.selectByPage(qo));
    }


    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @RequirePermission({"部门删除","department:delete"})
    public R delete(@PathVariable Long id) {
        departmentService.deleteById(id);
        return R.ok("删除成功");
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    @RequirePermission({"部门信息","department:info"})
    public R info(@PathVariable Long id) {
        return R.ok(departmentService.selectById(id));
    }


    @PostMapping("/saveOrUpdate")
    @ResponseBody
    @RequirePermission({"部门保存或更新","department:saveOrUpdate"})
    public R saveOrUpdate(@RequestBody Department department) {
        departmentService.saveOrUpdate(department);
        return R.ok("保存或者更新成功");
    }
}
