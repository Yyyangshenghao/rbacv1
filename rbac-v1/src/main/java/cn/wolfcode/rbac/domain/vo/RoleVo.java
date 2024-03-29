package cn.wolfcode.rbac.domain.vo;

import cn.wolfcode.rbac.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    private Role role;
    private Long[] permissionIds;
}
