package cn.wolfcode.rbac.service;


import cn.wolfcode.rbac.domain.Course;
import cn.wolfcode.rbac.domain.query.CourseQueryObject;
import cn.wolfcode.rbac.domain.vo.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICourseService {
    // 查询所有课程
    List<Course> selectAll();

    // 分页查询课程
    PageResult<Course> selectByPage(CourseQueryObject qo);

    // 根据课程id删除课程
    @Transactional
    void deleteById(int id);

    // 保存或更新课程信息
    @Transactional
    void saveOrUpdate(Course course);

    // 根据课程id查询课程信息
    Course selectById(int id);
}
