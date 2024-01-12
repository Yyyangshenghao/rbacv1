package cn.wolfcode.rbac.service;

import cn.wolfcode.rbac.domain.Department;
import cn.wolfcode.rbac.domain.query.QueryObject;
import cn.wolfcode.rbac.domain.vo.PageResult;

import java.util.List;

public interface IDepartmentService {
    List<Department> selectAll();
    void deleteById(Long id);
    void saveOrUpdate(Department department);
    Department selectById(Long id);
    PageResult<Department> selectByPage(QueryObject qo);
}
