package cn.wolfcode.rbac.web.controller;


import cn.wolfcode.rbac.domain.Course;
import cn.wolfcode.rbac.domain.Employee;
import cn.wolfcode.rbac.domain.query.CourseQueryObject;
import cn.wolfcode.rbac.domain.query.EmployeeQueryObject;
import cn.wolfcode.rbac.domain.vo.PageResult;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.service.ICourseService;
import cn.wolfcode.rbac.service.IEmployeeService;
import cn.wolfcode.rbac.utils.RequirePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/course")
@CrossOrigin(allowCredentials="true")
public class CourseApiController {
    @Autowired
    private ICourseService courseService;

    @GetMapping("/list")
    @ResponseBody
    @RequirePermission({"课程列表","course:list"})
    public R list(CourseQueryObject qo) {
        PageResult<Course> pageResult = courseService.selectByPage(qo);
        return R.ok(pageResult);
    }
}
