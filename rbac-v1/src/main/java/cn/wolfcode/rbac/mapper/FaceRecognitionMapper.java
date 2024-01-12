package cn.wolfcode.rbac.mapper;

import cn.wolfcode.rbac.domain.Face;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface FaceRecognitionMapper {

    int insert(Face face);

    Face selectById(Long employeeId);
    int updateById(Long employeeId);

    int save(Long employeeId, String Url);
}
