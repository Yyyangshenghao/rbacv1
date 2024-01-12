package cn.wolfcode.rbac.mapper;

import cn.wolfcode.rbac.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Role record);
    Role selectByPrimaryKey(Long id);
    List<Role> selectAll();
    int updateByPrimaryKey(Role record);
    List<Role> selectForList();
    void insertRelation(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
    int deleteRelation(Long roleId);
    List<Role> selectRolesByEmployee(Long employeeId);
}
