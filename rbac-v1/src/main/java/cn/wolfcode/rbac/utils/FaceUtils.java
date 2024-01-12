package cn.wolfcode.rbac.utils;

import org.opencv.core.*;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Component;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Component
public class FaceUtils {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    private CascadeClassifier faceDetector;

    public FaceUtils() {
        // 初始化人脸检测器，您需要指定Haar级联文件的路径
        this.faceDetector = new CascadeClassifier("E:\\IDEA CODE\\rbacv1\\rbac-v1\\src\\main\\resources\\classifiers\\haarcascade_frontalface_default.xml");
    }

    //裁剪出灰度人脸图片
    public byte[] detectAndCropFace(byte[] imageBytes) throws IOException {
        Mat src = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_UNCHANGED);

        MatOfRect faceDetections = new MatOfRect();

        Size minSize = new Size(200, 200);
        Size maxSize = new Size(600, 600);
        faceDetector.detectMultiScale(src, faceDetections,1.01,5,0,minSize,maxSize);

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
        Imgcodecs.imencode(".png", mat, mob);
        return mob.toArray();
    }

    //人脸模型训练
    public void trainEmployeePhotos(String folderPath, int employeeId) {
        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        File directory = new File(folderPath);

        FilenameFilter imgFilter = (dir, name) -> {
            name = name.toLowerCase();
            return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg");
        };

        File[] imageFiles = directory.listFiles(imgFilter);
        List<Mat> images = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                Mat img = Imgcodecs.imread(imageFile.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
                images.add(img);
                labels.add(employeeId);
            }
        }
        //如果文件夹中没有图片，则向前端返回错误

        MatOfInt labelsMat = new MatOfInt();
        labelsMat.fromList(labels);
        faceRecognizer.train(images, labelsMat);

        faceRecognizer.save("employee" + employeeId + "_model.xml");
    }
}
