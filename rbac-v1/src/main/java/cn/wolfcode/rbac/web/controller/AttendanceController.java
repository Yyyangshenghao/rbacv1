package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.domain.Attendance;
import cn.wolfcode.rbac.domain.vo.*;
import cn.wolfcode.rbac.service.IAttendanceService;
import cn.wolfcode.rbac.utils.RequirePermission;
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
    @RequirePermission({"发布签到","attendance:publish"})
    public R publish(@RequestBody AttendanceRequest requestBody){
        attendanceService.save(requestBody);
        return R.ok("签到发布成功");
    }

    @ResponseBody
    @GetMapping("/sessions/{classId}")
    @RequirePermission({"获取签到列表","attendance:sessions"})
    public R getAttendanceSessions(@PathVariable int classId){
        List<Attendance> attendanceList = attendanceService.getAttendancelist(classId);
        return R.ok(attendanceList);
    }

    @ResponseBody
    @GetMapping("/details/{attendanceId}")
    @RequirePermission({"获取签到详情","attendance:details"})
    public R detail(@PathVariable int attendanceId){
        List<Signin> signin = attendanceService.getSignin(attendanceId);
        return R.ok(signin);
    }

    @ResponseBody
    @PostMapping("/update")
    @RequirePermission({"更新签到状态","attendance:update"})
    public R update(@RequestBody SignVo signVo){
        attendanceService.updateAtt(signVo);
        return R.ok();
    }
}
