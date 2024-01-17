package cn.wolfcode.rbac.domain.vo;

import lombok.Data;

@Data
public class StudentSignIn {
    private int id;
    private String courseName;
    private String teacherName;
    private int status;
}
