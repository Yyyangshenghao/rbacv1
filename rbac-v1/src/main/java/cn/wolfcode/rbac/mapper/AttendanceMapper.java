package cn.wolfcode.rbac.mapper;

import cn.wolfcode.rbac.domain.vo.AttendanceRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    int insert(AttendanceRequest request);

    // 根据class_id获取所有的student_id
    List<Long> selectStudentIdsByClassId(int classId);

    // 批量添加学生的签到记录
    void insertAttendanceStudents(@Param("attendanceId") int attendanceId, @Param("studentIds") List<Long> studentIds);

    int selectByClId(int classId);
}
