package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.domain.vo.RecognitionRequest;
import cn.wolfcode.rbac.domain.vo.TrainRequest;
import cn.wolfcode.rbac.service.impl.FaceServiceImpl;
import cn.wolfcode.rbac.utils.FaceUtils;
import cn.wolfcode.rbac.utils.ObsUtils;
import com.obs.services.exception.ObsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                obsUtils.downloadFile(filename, localImagePath);
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

    @PostMapping("/recognize")
    public R recognizeFace(@RequestBody RecognitionRequest recognitionRequest){
        try {
            //从Obs下载模型到本地保存
            Long employeeId = recognitionRequest.getEmployeeId();
            String filename = "models/employee" + employeeId + "_model.xml";
            String localModelPath = "rbac-v1/src/main/resources/" + filename;
            // 检查本地是否已有模型文件，如果没有，则从OBS下载
            if (!Files.exists(Paths.get(localModelPath))) {
                obsUtils.downloadFile(filename, localModelPath);
            }

            //将传入的base64编码进行识别
            String base64Image = recognitionRequest.getImage();
            boolean isRecognized = faceUtils.recognizeFace(base64Image, localModelPath);

            if(isRecognized){
                return R.ok("Face recognized successfully.");
            } else{
                return R.error("Face not recognized");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("An error occurred while processing the image.");
        }
    }
}