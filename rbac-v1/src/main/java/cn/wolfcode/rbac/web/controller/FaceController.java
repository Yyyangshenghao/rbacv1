package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.service.impl.FaceServiceImpl;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/face")
@CrossOrigin(allowCredentials="true")
public class FaceController{

    @Autowired
    private FaceServiceImpl faceService;





}