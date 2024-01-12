package cn.wolfcode.rbac.utils;

import cn.wolfcode.rbac.domain.Face;
import cn.wolfcode.rbac.domain.vo.R;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.PutObjectResult;
import com.obs.services.model.UploadFileRequest;
import org.apache.logging.log4j.core.Filter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

@Component
public class ObsUtils {
    //    private String ak = System.getenv("ACCESS_KEY_ID");
    private String ak = "RVUPLCJVDV7ZIH5Y9XEF";
    //    private String sk = System.getenv("SECRET_ACCESS_KEY_ID");
    private String sk = "Cen2Sy1tIq9YRmbfEDI6QSSzyGpiCd2LC5ftgLgc";
    private String endpoint = "https://obs.cn-east-3.myhuaweicloud.com";

    private ObsClient createObsClient() {
        return new ObsClient(ak, sk, endpoint);
    }

    @Autowired
    private FaceUtils faceUtils;

//    private byte[] preprocessImage(byte[] imageBytes) throws IOException {
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
//        BufferedImage originalImage = ImageIO.read(byteArrayInputStream);
//
//        // 灰度化
//        BufferedImage grayImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
//        grayImage.getGraphics().drawImage(originalImage, 0, 0, null);
//
//        // 直方图均衡化
//        BufferedImage equalizedImage = new BufferedImage(grayImage.getWidth(), grayImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
//        equalizedImage.getGraphics().drawImage(grayImage, 0, 0, null);
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ImageIO.write(equalizedImage, "jpg", byteArrayOutputStream);
//        return byteArrayOutputStream.toByteArray();
//    }

    public String uploadImage(String base64Image, String objectKey) throws IOException {
        ObsClient obsClient = null;
        try {
            obsClient = createObsClient();
            byte[] imageBytes = Base64.decodeBase64(base64Image);
            // 对图像进行灰度化和直方图均衡化预处理
            imageBytes = faceUtils.detectAndCropFace(imageBytes);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            //传入Obs中
            obsClient.putObject("rjwd", objectKey, inputStream);
            //返回保存图片的Url
            return "https://rjwd.obs.cn-east-3.myhuaweicloud.com/" + objectKey;
        } finally {
            if (obsClient != null) {
                try {
                    obsClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
