package cn.wolfcode.rbac.service;

import cn.wolfcode.rbac.domain.Face;

import java.util.List;

public interface IFaceService {
    void insert(Face face);

    List<String> selectById(Long employeeId);
}