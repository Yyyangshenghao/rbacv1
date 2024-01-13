package cn.wolfcode.rbac.service;

import cn.wolfcode.rbac.domain.Face;

public interface IFaceService {
    void insert(Face face);

    void save(Long employeeId, String Url);
}