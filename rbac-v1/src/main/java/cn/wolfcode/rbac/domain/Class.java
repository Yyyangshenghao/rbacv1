package cn.wolfcode.rbac.domain;

import lombok.Data;

@Data
public class Class {
    private int id;//教学班id
    private Course course;//课程
    private Employee teacher;//老师
    private String classname;//教学班名称
    private String classroom;//上课教室
    private String time;//上课时间
}
