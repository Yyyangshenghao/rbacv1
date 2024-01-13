package cn.wolfcode.rbac.service.impl;

import cn.wolfcode.rbac.domain.Face;
import cn.wolfcode.rbac.mapper.FaceMapper;
import cn.wolfcode.rbac.service.IFaceService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class FaceImpl implements IFaceService {

    @Autowired
    private FaceMapper faceMapper;


    @Override
    public void insert(Face face) {
        faceMapper.insert(face);
    }

    @Override
    public void save(Long employeeId, String Url) {
        faceMapper.save(employeeId,Url);
    }


}

