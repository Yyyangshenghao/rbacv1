package cn.wolfcode.rbac.domain;


import lombok.Data;



@Data
public class Attendance {
    private int id;
    private int status;
    private String startTime;
    private String endTime;

}
