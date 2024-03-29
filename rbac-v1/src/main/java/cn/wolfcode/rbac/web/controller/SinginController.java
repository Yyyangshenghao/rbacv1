package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.domain.vo.StudentSignIn;
import cn.wolfcode.rbac.service.IAttendanceService;
import cn.wolfcode.rbac.utils.RequirePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/singin")
@CrossOrigin(allowCredentials="true")
public class SinginController {

    @Autowired
    private IAttendanceService attendanceService;

    @ResponseBody
    @GetMapping("/listForStudent/{studentId}")
    @RequirePermission({"学生签到列表","signin:listForStudent"})
    public R list(@PathVariable Long studentId){
        List<StudentSignIn> list = attendanceService.list(studentId);
        return R.ok(list);
    }
}
