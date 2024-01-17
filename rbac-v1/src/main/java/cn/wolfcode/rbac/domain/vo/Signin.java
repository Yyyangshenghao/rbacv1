package cn.wolfcode.rbac.domain.vo;

import lombok.Data;

@Data
public class Signin {
    private int attendanceId;
    private Long employeeId;
    private String name;
    private int status;
}
