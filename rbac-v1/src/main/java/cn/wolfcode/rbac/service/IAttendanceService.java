package cn.wolfcode.rbac.service;

import cn.wolfcode.rbac.domain.Attendance;
import cn.wolfcode.rbac.domain.vo.AttendanceRequest;
import cn.wolfcode.rbac.domain.vo.Signin;

import java.util.List;

public interface IAttendanceService {

    void save(AttendanceRequest requestBody);

    List<Attendance> getAttendancelist(int classId);

    List<Signin> getSignin(int attendanceId);
}
