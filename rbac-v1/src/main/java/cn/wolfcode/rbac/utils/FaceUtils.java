package cn.wolfcode.rbac.utils;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Component
public class FaceUtils {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    private CascadeClassifier faceDetector;

    public FaceUtils() {
        // 初始化人脸检测器，您需要指定Haar级联文件的路径
        this.faceDetector = new CascadeClassifier("src/main/resources/classifiers/haarcascade_frontalface_default.xml");
    }

    public byte[] detectAndCropFace(byte[] imageBytes) throws IOException {
        Mat src = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_UNCHANGED);

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(src, faceDetections);

        for (Rect rect : faceDetections.toArray()) {
            // 裁剪人脸区域
            Mat face = new Mat(src, rect);

            // 对人脸图像进行进一步处理
            Mat processedFace = processFace(face);

            // 将处理后的Mat转换为字节数组
            return matToByteArray(processedFace);
        }

        // 如果没有检测到人脸，返回原始图像的处理结果或null
        return null; // 或者返回原始图像的处理结果
    }

    private Mat processFace(Mat face) {
        // 将人脸转换为灰度图像
        Mat grayFace = new Mat();
        Imgproc.cvtColor(face, grayFace, Imgproc.COLOR_BGR2GRAY);

        // 进行直方图均衡化
        Mat equalizedFace = new Mat();
        Imgproc.equalizeHist(grayFace, equalizedFace);

        return equalizedFace;
    }

    private byte[] matToByteArray(Mat mat) throws IOException {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, mob);
        return mob.toArray();
    }
}
