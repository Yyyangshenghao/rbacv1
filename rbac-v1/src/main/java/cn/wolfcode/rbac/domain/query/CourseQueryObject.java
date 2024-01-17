package cn.wolfcode.rbac.domain.query;

import lombok.Data;

@Data
public class CourseQueryObject extends QueryObject{
    private String keyword; //模糊查询参数
}
