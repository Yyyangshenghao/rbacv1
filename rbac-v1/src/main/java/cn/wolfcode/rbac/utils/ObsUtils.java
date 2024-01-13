package cn.wolfcode.rbac.utils;

import cn.wolfcode.rbac.domain.Face;
import cn.wolfcode.rbac.domain.vo.R;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;
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

    //先预处理图像，后上传至Obs
    public String uploadImage(String base64Image, String objectKey) {
        ObsClient obsClient = null;
        try {
            obsClient = createObsClient();
            byte[] imageBytes = Base64.decodeBase64(base64Image);
            // 对图像进行灰度化和直方图均衡化预处理并裁剪出人脸
            imageBytes = faceUtils.detectAndCropFace(imageBytes);
            if (imageBytes == null) {
                // 没有检测到人脸，返回特定的错误标识
                return "NO_FACE_DETECTED";
            }
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

    //从Obs端下载图片到本地
    public void downloadImage(String objectKey, String localFilePath) throws IOException {
        ObsClient obsClient = null;
        try {
            obsClient = createObsClient();
            ObsObject obsObject = obsClient.getObject(new GetObjectRequest("rjwd", objectKey));
            InputStream inputStream = obsObject.getObjectContent();

            // 将输入流写入文件
            File file = new File(localFilePath);
            // 确保文件的父目录存在
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] bytes = new byte[1024];
                int read;
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } finally {
                inputStream.close();
            }
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
