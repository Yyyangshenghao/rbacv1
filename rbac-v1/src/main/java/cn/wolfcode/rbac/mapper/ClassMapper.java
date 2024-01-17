package cn.wolfcode.rbac.mapper;

import cn.wolfcode.rbac.domain.Class;
import cn.wolfcode.rbac.domain.query.QueryObject;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
@Mapper
public interface ClassMapper {
    // 查询所有课程
    List<Class> selectAll();


    List<Class> selectById(Long teacherId);
}
