package cn.wolfcode.rbac.domain.vo;

import lombok.Data;

@Data
public class ClassVo {
    private int id;
    private String cname;//课程名称
    private String tname;//教师名称
    private String clname;//教学班名称
    private String classroom;//教师
    private String time;//上课时间
}
