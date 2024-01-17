package cn.wolfcode.rbac.service.impl;

import cn.wolfcode.rbac.domain.Attendance;
import cn.wolfcode.rbac.domain.vo.AttendanceRequest;
import cn.wolfcode.rbac.domain.vo.PageResult;
import cn.wolfcode.rbac.domain.vo.Signin;
import cn.wolfcode.rbac.mapper.AttendanceMapper;
import cn.wolfcode.rbac.service.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
