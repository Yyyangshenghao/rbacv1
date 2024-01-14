package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.domain.Face;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.domain.vo.TrainRequest;
import cn.wolfcode.rbac.service.impl.FaceServiceImpl;
import cn.wolfcode.rbac.utils.FaceUtils;
import cn.wolfcode.rbac.utils.ObsUtils;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.obs.services.exception.ObsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/face")
@CrossOrigin(allowCredentials="true")
public class FaceController{

    @Autowired
    private FaceServiceImpl faceService;
    @Autowired
    private ObsUtils obsUtils;
    @Autowired
    private FaceUtils faceUtils;

    @PostMapping("/train")
    public R trainPhotos(@RequestBody TrainRequest trainRequest) {
        if (trainRequest == null || trainRequest.getEmployeeId() == null) {
            return R.error("请求参数无效");
        }

        Long employeeId = trainRequest.getEmployeeId();
        List<String> objectKeys;
        try {
            objectKeys = faceService.selectById(employeeId);
        } catch (Exception e) {
            return R.error("获取人脸数据失败：" + e.getMessage());
        }
        //设置图片保存路径
        String imagesDirectoryPath = "rbac-v1/src/main/resources/images/";
        File imagesDirectory = new File(imagesDirectoryPath);
        if (!imagesDirectory.exists()) {
            imagesDirectory.mkdirs();
        }

        try {
            //从Obs下载图片到本地保存
            for (String filename : objectKeys) {
                String localImagePath = imagesDirectoryPath + filename;
                obsUtils.downloadImage(filename, localImagePath);
            }
            //训练模型
            String modelPath = imagesDirectoryPath + "employee-photos/" + employeeId;
            faceUtils.train(employeeId, modelPath);

            //上传Obs
            String savePath = "models/employee" + employeeId + "_model.xml";
            String modelFile = "rbac-v1/src/main/resources/models/employee" + employeeId + "_model.xml";
            obsUtils.uploadModel(savePath, modelFile);
            return R.ok("训练成功,并上传到Obs");
        } catch (IOException e) {
            return R.error("训练模型失败：" + e.getMessage());
        } catch (ObsException e) {
            return R.error("上传模型到OBS失败：" + e.getMessage());
        }
    }


}