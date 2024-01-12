package cn.wolfcode.rbac.service;

import cn.wolfcode.rbac.domain.Role;
import cn.wolfcode.rbac.domain.query.QueryObject;
import cn.wolfcode.rbac.domain.vo.PageResult;
import cn.wolfcode.rbac.domain.vo.RoleVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IRoleService {
    /**
     * @param id
     * 删除角色
     */
    void deleteById(Long id);

    void saveOrUpdate(RoleVo roleVo);

    Role selectById(Long id);

    List<Role> listAll();

    // 分页查询的方法
    PageResult<Role> selectByPage(QueryObject qo);

    List<Role> queryRolesByEmployeeId(Long employeeId);
}
