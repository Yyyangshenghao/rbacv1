package cn.wolfcode.rbac.mapper;

import cn.wolfcode.rbac.domain.Course;
import cn.wolfcode.rbac.domain.query.CourseQueryObject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    // 根据id查询课程信息
    Course selectById(int id);
    // 查询所有课程
    List<Course> selectAll();
    // 根据课程id删除课程
    int deleteById(int id);
    // 添加课程
    int insert(Course course);
    // 根据课程id更新课程
    int updateById(Course course);
    // 查询分页数据
    List<Course> queryForList(CourseQueryObject qo);
}
