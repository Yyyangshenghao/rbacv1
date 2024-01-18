package cn.wolfcode.rbac.service.impl;

import cn.wolfcode.rbac.domain.Attendance;
import cn.wolfcode.rbac.domain.vo.*;
import cn.wolfcode.rbac.mapper.AttendanceMapper;
import cn.wolfcode.rbac.service.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceServiceImpl implements IAttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    @Transactional
    public void save(AttendanceRequest requestBody) {
        attendanceMapper.insert(requestBody);
        // 获取班级内所有学生的ID列表
        List<Long> studentIds = attendanceMapper.selectStudentIdsByClassId(requestBody.getClassId());
        int id = attendanceMapper.selectByClId(requestBody.getClassId());
        // 为每个学生添加签到记录
        attendanceMapper.insertAttendanceStudents(id, studentIds);
    }

    @Override
    public List<Attendance> getAttendancelist(int classId){
//        System.out.println(attendanceMapper.getAttendance(classId));
        return attendanceMapper.getAttendance(classId);
    }

    @Override
    public List<Signin> getSignin(int attendanceId) {
        return attendanceMapper.getSignin(attendanceId);
    }

    @Override
    public List<StudentSignIn> list(Long studentId) {
        return attendanceMapper.listforstudent(studentId);
    }

    @Override
    public void updateAtt(SignVo signVo) {
        attendanceMapper.updateAtt(signVo);
    }

    @Scheduled(cron = "* * * * * ?")//每秒触发
    @Transactional
    public void updateExpiredAttendances() {
       // 如果有，执行更新
        attendanceMapper.updateExpiredAttendances();
    }

}
