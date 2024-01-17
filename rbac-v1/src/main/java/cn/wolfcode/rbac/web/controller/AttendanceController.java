package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.domain.vo.AttendanceRequest;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.service.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
