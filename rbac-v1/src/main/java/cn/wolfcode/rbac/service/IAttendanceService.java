package cn.wolfcode.rbac.service;

import cn.wolfcode.rbac.domain.Attendance;
import cn.wolfcode.rbac.domain.vo.AttendanceRequest;
import cn.wolfcode.rbac.domain.vo.SignVo;
import cn.wolfcode.rbac.domain.vo.Signin;
import cn.wolfcode.rbac.domain.vo.StudentSignIn;

import java.util.List;

public interface IAttendanceService {

    void save(AttendanceRequest requestBody);

    List<Attendance> getAttendancelist(int classId);

    List<Signin> getSignin(int attendanceId);

    List<StudentSignIn> list(Long studentId);

    void updateAtt(SignVo signVo);

}
