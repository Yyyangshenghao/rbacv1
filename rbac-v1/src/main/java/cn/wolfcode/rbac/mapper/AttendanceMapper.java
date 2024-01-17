package cn.wolfcode.rbac.mapper;

import cn.wolfcode.rbac.domain.Attendance;
import cn.wolfcode.rbac.domain.vo.AttendanceRequest;
import cn.wolfcode.rbac.domain.vo.SignVo;
import cn.wolfcode.rbac.domain.vo.Signin;
import cn.wolfcode.rbac.domain.vo.StudentSignIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface AttendanceMapper {
    int insert(AttendanceRequest request);

    // 根据class_id获取所有的student_id
    List<Long> selectStudentIdsByClassId(int classId);

    // 批量添加学生的签到记录
    void insertAttendanceStudents(@Param("attendanceId") int attendanceId, @Param("studentIds") List<Long> studentIds);

    int selectByClId(int classId);

    List<Attendance> getAttendance(int classId);

    List<Signin> getSignin(int attendanceId);

    List<StudentSignIn> listforstudent(Long employeeId);

    int updateAtt(SignVo signVo);

//    void updateExpiredAttendances();

}
