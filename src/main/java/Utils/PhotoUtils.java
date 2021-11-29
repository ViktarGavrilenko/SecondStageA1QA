package Utils;

import aquality.selenium.core.logging.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class PhotoUtils {
    private static final int ALLOWABLE_MATCH_PERCENTAGE = 95;
    private static final int ONE_HUNDRED = 100;

    public static boolean compareImageOpenCV(String fileA, String fileB) {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat frameA = Imgcodecs.imread(fileA);
        Mat frameB = Imgcodecs.imread(fileB);

        Mat tmp1 = new Mat(frameA.rows(), frameA.cols(), CvType.CV_8UC1);
        Imgproc.cvtColor(frameA, tmp1, Imgproc.COLOR_RGB2GRAY);

        Mat tmp2 = new Mat(frameB.rows(), frameB.cols(), CvType.CV_8UC1);
        Imgproc.cvtColor(frameB, tmp2, Imgproc.COLOR_RGB2GRAY);

        Mat subtractResult = new Mat(frameB.rows(), frameB.cols(), CvType.CV_8UC1);
        Core.absdiff(frameA, frameB, subtractResult);
        Imgproc.threshold(subtractResult, subtractResult, 50, 1, Imgproc.THRESH_BINARY);
        Scalar sumDiff = Core.sumElems(subtractResult);
        double diffRatio = ONE_HUNDRED - sumDiff.val[0] / (frameA.cols() * frameB.rows()) * ONE_HUNDRED;
        Logger.getInstance().info("Similarity percentage " + String.format("%.2f", diffRatio) + " %");
        return diffRatio > ALLOWABLE_MATCH_PERCENTAGE;
    }
}