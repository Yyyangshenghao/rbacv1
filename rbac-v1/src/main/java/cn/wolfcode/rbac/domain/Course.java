package cn.wolfcode.rbac.domain;

import lombok.Data;

@Data
public class Course {
    private int id;
    private String courseName;
    private float credit;
    private String hour;
}
