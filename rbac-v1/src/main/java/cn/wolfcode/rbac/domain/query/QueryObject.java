package cn.wolfcode.rbac.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryObject {
    private int pageNum=1;// 当前页
    private int pageSize=5;// 每页显示多少条
}
