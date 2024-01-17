package cn.wolfcode.rbac.service;

import cn.wolfcode.rbac.domain.Class;
import cn.wolfcode.rbac.domain.query.QueryObject;
import cn.wolfcode.rbac.domain.vo.PageResult;

import java.util.List;

public interface IClassService {
    PageResult<Class> selectByPage(QueryObject qo);

    List<Class> selectById(Long teacherId);
}
