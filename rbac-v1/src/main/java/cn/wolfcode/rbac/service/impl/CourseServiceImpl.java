package cn.wolfcode.rbac.service.impl;

import cn.wolfcode.rbac.domain.Course;
import cn.wolfcode.rbac.domain.query.CourseQueryObject;
import cn.wolfcode.rbac.domain.vo.PageResult;
import cn.wolfcode.rbac.mapper.CourseMapper;
import cn.wolfcode.rbac.service.ICourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    CourseMapper courseMapper;


    @Override
    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }

    @Override
    public PageResult<Course> selectByPage(CourseQueryObject qo) {
        PageHelper.startPage(qo.getPageNum(),qo.getPageSize());
        PageInfo pageInfo=new PageInfo(courseMapper.queryForList(qo));
        PageResult pageResult=new PageResult();
        BeanUtils.copyProperties(pageInfo,pageResult);
        return pageResult;
    }

    @Override
    public void deleteById(int id) {
        Assert.notNull(id>=0,"非法參數");
        Assert.state(courseMapper.deleteById(id)>0,"删除失败");
    }

    @Override
    public void saveOrUpdate(Course course) {
        if (course != null) {
            if (course.getId() > 0) {
                // ID 存在，执行更新操作
                courseMapper.updateById(course);
            } else {
                // ID 不存在，执行插入操作
                courseMapper.insert(course);
            }
        } else {
            throw new IllegalArgumentException("课程信息不能为空");
        }
    }

    @Override
    public Course selectById(int id) {
        return courseMapper.selectById(id);
    }
}
