package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.domain.Face;
import cn.wolfcode.rbac.domain.vo.ImageUploadRequest;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.service.IFaceService;
import cn.wolfcode.rbac.utils.ObsUtils;
import cn.wolfcode.rbac.utils.RequirePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(allowCredentials="true")
public class ImageUploadController {

    @Autowired
    private ObsUtils obsUtils;

    @Autowired
    private IFaceService faceService;

    @PostMapping("/upload-image")
    @RequirePermission({"上传人脸","image:upload"})
    public R uploadImage(@RequestBody ImageUploadRequest request) {
        try {
            String base64Image = request.getBase64Image();
            Long employeeId = request.getEmployeeId();
            String objectKey = "employee-photos/" + employeeId + "/" +  employeeId + "-" + UUID.randomUUID().toString() + ".png";

            // 这里处理base64Image，转换并上传到OBS
            String fileUrl = obsUtils.uploadImage(base64Image,objectKey);
            System.out.println(fileUrl);

            //在uploadImage方法的处理中未裁剪出人脸的情况
            if ("NO_FACE_DETECTED".equals(fileUrl)) {
                // 如果没有检测到人脸
                return R.error("No face detected in the image.");
            }

            Face face = new Face();
            face.setEmployeeId(employeeId);
            face.setObjectKey(objectKey);
            faceService.insert(face);
            return R.ok("Image uploaded successfully. URL: " + fileUrl);

        } catch (Exception e) {
            return R.error("Error uploading image: " + e.getMessage());
        }
    }



}
