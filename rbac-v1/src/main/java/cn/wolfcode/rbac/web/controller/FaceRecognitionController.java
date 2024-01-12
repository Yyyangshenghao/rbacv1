//package cn.wolfcode.rbac.web.controller;
//
//import cn.wolfcode.rbac.domain.Face;
//import cn.wolfcode.rbac.domain.vo.ImageUploadRequest;
//import cn.wolfcode.rbac.domain.vo.R;
//import cn.wolfcode.rbac.mapper.FaceRecognitionMapper;
//import cn.wolfcode.rbac.service.IFaceRecognitionService;
//import cn.wolfcode.rbac.service.impl.FaceRecognitionImpl;
//import cn.wolfcode.rbac.utils.ObsUtils;
//import com.obs.services.model.UploadFileRequest;
//import org.apache.ibatis.logging.stdout.StdOutImpl;
//import org.opencv.core.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.objdetect.CascadeClassifier;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Base64;
//
//@RestController
//@RequestMapping("/api/face")
//@CrossOrigin(allowCredentials="true")
//public class FaceRecognitionController {
//
//    @Autowired
//    private IFaceRecognitionService faceRecognitionService;
//
//    @Autowired
//    private ObsUtils obsUtils;
//
////    @GetMapping("/getUrl")
////    @ResponseBody
////    public R uploadImage(@RequestBody Face face){
////        faceRecognitionService.insert(face);
////        return R.ok("成功获取Url");
////    }
//
//
//    @PostMapping("/upload")
//    @ResponseBody
//    public R uploadToObs(@RequestBody ImageUploadRequest request){
//        try {
//            String base64Image = request.getBase64Image();
//            Long employeeId = request.getEmployeeId();
//
//            if (base64Image == null || base64Image.isEmpty()) {
//                System.out.println("hilo");
//                return R.error("上传的图片不能为空");
//            }
//            else{
//                System.out.println("bukong");
//            }
//            // 调用ObsUtils上传图片
//            String fileUrl = obsUtils.uploadImage(base64Image, employeeId);
//            faceRecognitionService.save(employeeId,fileUrl);
//
//            return fileUrl != null ? R.ok("图片上传成功", fileUrl) : R.error("图片上传失败");
//        } catch (Exception e) {
//            System.out.println("fuck");
//            return R.error("图片上传异常: " + e.getMessage());
//        }
//    }
//}
