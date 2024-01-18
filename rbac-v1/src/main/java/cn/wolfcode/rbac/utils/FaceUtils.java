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
import java.util.Base64;
import java.util.List;

@Component
public class FaceUtils {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    // 置信度阈值
    private static final double CONFIDENCE_THRESHOLD = 34.0; // 示例阈值

    private CascadeClassifier faceDetector;

    public FaceUtils() {
        // 初始化人脸检测器，您需要指定Haar级联文件的路径
        this.faceDetector = new CascadeClassifier("rbac-v1/src/main/resources/classifiers/haarcascade_frontalface_default.xml");
    }

    //裁剪出灰度人脸图片
    public byte[] detectAndCropFace(byte[] imageBytes) {
        Mat src = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_UNCHANGED);

        MatOfRect faceDetections = new MatOfRect();

        Size minSize = new Size(150, 150);
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

    private byte[] matToByteArray(Mat mat) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".png", mat, mob);
        return mob.toArray();
    }

    //人脸模型训练
    public void train(Long employeeId,String folderPath) throws IOException {
        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        File directory = new File(folderPath);

        FilenameFilter imgFilter = (dir, name) -> {
            name = name.toLowerCase();
            return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg");
        };

        File[] imageFiles = directory.listFiles(imgFilter);

        if (imageFiles == null || imageFiles.length == 0) {
            // 如果文件夹中没有图像文件，抛出异常
            throw new IOException("No images found in directory: " + folderPath);
        }

        List<Mat> images = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        for (File imageFile : imageFiles) {
            Mat img = Imgcodecs.imread(imageFile.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
            images.add(img);
            labels.add(Math.toIntExact(employeeId));
        }

        MatOfInt labelsMat = new MatOfInt();
        labelsMat.fromList(labels);
        faceRecognizer.train(images, labelsMat);

        String modelPath = "rbac-v1/src/main/resources/models/employee" + employeeId + "_model.xml";
        faceRecognizer.save(modelPath);
    }

    /**
     * 识别由Base64编码的单个人脸图像。
     * @param base64Image Base64编码的人脸图像
     * @param modelPath 人脸识别模型的路径
     * @return 识别出的用户ID，如果没有匹配则返回"Not recognized"。
     */
    public boolean recognizeFace(String base64Image, String modelPath) {
        try {
            // 将Base64编码的图像转换为字节数组
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // 使用detectAndCropFace方法来检测和裁剪人脸
            byte[] faceBytes = detectAndCropFace(imageBytes);
            if (faceBytes == null) {
                return false; // 没有检测到人脸
            }

            // 将裁剪后的人脸字节数组转换成OpenCV的Mat对象
            Mat face = Imgcodecs.imdecode(new MatOfByte(faceBytes), Imgcodecs.IMREAD_GRAYSCALE);

            // 加载人脸识别模型
            FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
            faceRecognizer.read(modelPath);

            // 创建一个数组来存储预测结果
            int[] label = new int[1];
            double[] confidence = new double[1];

            // 进行人脸识别
            faceRecognizer.predict(face, label, confidence);

            // 检查置信度，如果置信度低于阈值且预测标签有效，则认为识别成功
            return confidence[0] < CONFIDENCE_THRESHOLD && label[0] != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 在发生异常时返回false
        }
    }


}
