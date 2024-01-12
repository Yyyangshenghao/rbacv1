package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.domain.vo.ImageUploadRequest;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.utils.ObsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ImageUploadController {

    @Autowired
    private ObsUtils obsUtils;

    @PostMapping("/upload-image")
    public R uploadImage(@RequestBody ImageUploadRequest request) {
        try {
            String base64Image = request.getBase64Image();
            Long employeeId = request.getEmployeeId();

            // 这里处理base64Image，转换并上传到OBS
            String fileUrl = obsUtils.uploadImage(base64Image,"employee-photos/" + employeeId + "/" +  employeeId + "-" + UUID.randomUUID().toString() + ".png");
            System.out.println(fileUrl);

            //在uploadImage方法的处理中未裁剪出人脸的情况
            if ("NO_FACE_DETECTED".equals(fileUrl)) {
                // 如果没有检测到人脸
                return R.error("No face detected in the image.");
            }

            return R.ok("Image uploaded successfully. URL: " + fileUrl);

        } catch (Exception e) {
            return R.error("Error uploading image: " + e.getMessage());
        }
    }
}
