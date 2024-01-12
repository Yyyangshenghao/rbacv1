package cn.wolfcode.rbac.service;

import cn.wolfcode.rbac.domain.Permission;
import cn.wolfcode.rbac.domain.query.QueryObject;
import cn.wolfcode.rbac.domain.vo.PageResult;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

public interface IPermissionService {
    // 查询所有权限
    List<Permission> listAll();
    // 分页查询的方法
    PageResult<Permission> query(QueryObject qo);
    //  通过角色id查询对应权限
    List<Permission> queryByRoleId(Long id);
    // 持久化方法声明权限
    void load();
}
