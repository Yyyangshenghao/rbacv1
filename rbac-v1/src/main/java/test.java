import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class test {
    static {
        // 加载OpenCV本地库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        // 读取图像文件
        Mat image = Imgcodecs.imread("C:\\Users\\24711\\Desktop\\07-RBAC\\code\\rbacv1\\rbac-v1\\src\\main\\java\\test.png");

        // 检查图像是否加载成功
        if (image.empty()) {
            System.out.println("图像加载失败！");
            return;
        } else {
            System.out.println("图像加载成功！");
        }

        // 显示图像
        HighGui.imshow("测试图像", image);
        HighGui.waitKey(0);

        // 释放所有窗口
        HighGui.destroyAllWindows();
    }
}
