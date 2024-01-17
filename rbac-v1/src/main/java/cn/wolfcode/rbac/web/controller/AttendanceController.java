package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.domain.Attendance;
import cn.wolfcode.rbac.domain.vo.AttendanceRequest;
import cn.wolfcode.rbac.domain.vo.PageResult;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.domain.vo.Signin;
import cn.wolfcode.rbac.service.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/attendance")
@CrossOrigin(allowCredentials="true")
public class AttendanceController {
    @Autowired
    private IAttendanceService attendanceService;

    @ResponseBody
    @PostMapping("/publish")
    public R publish(@RequestBody AttendanceRequest requestBody){
        attendanceService.save(requestBody);
        return R.ok("签到发布成功");
    }

    @ResponseBody
    @GetMapping("/sessions/{classId}")
    public R getAttendanceSessions(@PathVariable int classId){
        List<Attendance> attendanceList = attendanceService.getAttendancelist(classId);
        return R.ok(attendanceList);
    }

    @ResponseBody
    @GetMapping("/details/{attendanceId}")
    public R detail(@PathVariable int attendanceId){
        List<Signin> signin = attendanceService.getSignin(attendanceId);
        return R.ok(signin);
    }
}
