package cn.wolfcode.rbac.service.impl;

import cn.wolfcode.rbac.domain.Face;
import cn.wolfcode.rbac.mapper.FaceMapper;
import cn.wolfcode.rbac.service.IFaceService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


@Service
public class FaceServiceImpl implements IFaceService {

    @Autowired
    private FaceMapper faceMapper;


    @Override
    public void insert(Face face) {
        faceMapper.insert(face);
    }

    @Override
    public Face selectById(Long employeeId) {
        Assert.notNull(employeeId, "非法参数");
        return faceMapper.selectById(employeeId);
    }
}

