package cn.wolfcode.rbac.web.controller;


import cn.wolfcode.rbac.domain.Employee;
import cn.wolfcode.rbac.domain.query.EmployeeQueryObject;
import cn.wolfcode.rbac.domain.vo.AdminStateVo;
import cn.wolfcode.rbac.domain.vo.EmployeeRoleVo;
import cn.wolfcode.rbac.domain.vo.PageResult;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.service.IDepartmentService;
import cn.wolfcode.rbac.service.IEmployeeService;
import cn.wolfcode.rbac.utils.RequirePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/employee")
@CrossOrigin(allowCredentials="true")
public class EmployeeApiController {
    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/list")
    @ResponseBody
    @RequirePermission({"员工列表","employee:list"})
    public R list(EmployeeQueryObject qo) {
        PageResult<Employee> pageResult = employeeService.selectByPage(qo);
        return R.ok(pageResult);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @RequirePermission({"员工删除","employee:delete"})
    public R delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return R.ok("删除成功");
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    @RequirePermission({"员工信息","employee:info"})
    public R info(@PathVariable Long id) {
        return R.ok(employeeService.selectById(id));
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    @RequirePermission({"员工保存或更新","employee:saveOrUpdate"})
    public R saveOrUpdate(@RequestBody EmployeeRoleVo employeeRoleVo){
        employeeService.saveOrUpadate(employeeRoleVo);
        return R.ok("保存或者更新成功");
    }

    @PostMapping("/updateState")
    @ResponseBody
    @RequirePermission({"更新管理员","employee:updateState"})
    public R updateState(@RequestBody AdminStateVo adminStateVo){
        employeeService.updateStateById(adminStateVo);
        return R.ok("保存或者更新成功");
    }
}
