package cn.wolfcode.rbac.utils;

import cn.wolfcode.rbac.domain.vo.R;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.PutObjectResult;
import com.obs.services.model.UploadFileRequest;
import org.apache.logging.log4j.core.Filter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    public String uploadImage(String base64Image, String objectKey) throws IOException {
        ObsClient obsClient = null;
        try {
            obsClient = createObsClient();
            byte[] imageBytes = Base64.decodeBase64(base64Image);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            obsClient.putObject("rjwd", objectKey, inputStream);
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
