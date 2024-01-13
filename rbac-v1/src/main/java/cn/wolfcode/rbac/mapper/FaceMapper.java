package cn.wolfcode.rbac.mapper;

import cn.wolfcode.rbac.domain.Face;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface FaceMapper {

    int insert(Face face);

    Face selectById(Long employeeId);

    int updateById(Long employeeId);


}
