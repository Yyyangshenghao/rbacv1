package cn.wolfcode.rbac.mapper;

import cn.wolfcode.rbac.domain.Face;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FaceMapper {

    int insert(Face face);

    List<String> selectById(Long employeeId);

    int updateById(Long employeeId);


}
