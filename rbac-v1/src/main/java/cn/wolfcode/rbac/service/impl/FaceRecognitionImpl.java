package cn.wolfcode.rbac.service.impl;

import cn.wolfcode.rbac.domain.Face;
import cn.wolfcode.rbac.mapper.FaceRecognitionMapper;
import cn.wolfcode.rbac.service.IFaceRecognitionService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class FaceRecognitionImpl implements IFaceRecognitionService {

    @Autowired
    private FaceRecognitionMapper faceRecognitionMapper;


    @Override
    public void insert(Face face) {
        faceRecognitionMapper.insert(face);
    }

    @Override
    public void save(Long employeeId, String Url) {
        faceRecognitionMapper.save(employeeId,Url);
    }


}

