package Utils;

import aquality.selenium.core.logging.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static Utils.MathUtils.roundAvoid;

public class PhotoUtils {
    private static final double ALLOWABLE_MATCH_PERCENTAGE = 30;
    private static final int ONE_HUNDRED = 100;
    private static final int NUMBER_OF_ROUNDING_DIGITS = 2;

    public static boolean compareImage(File fileA, File fileB) {
        float percentage;
        float countFalse = 0;
        float countPixels = 0;
        float coefficient = 1;
        try {
            BufferedImage imageA = ImageIO.read(fileA);
            BufferedImage imageB = ImageIO.read(fileB);

            if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight()) {
                coefficient = roundAvoid((float) imageA.getWidth() / imageB.getWidth(), NUMBER_OF_ROUNDING_DIGITS);
                if (coefficient !=
                        roundAvoid((float) imageA.getHeight() / imageB.getHeight(), NUMBER_OF_ROUNDING_DIGITS)) {
                    Logger.getInstance().error("The sides of the pictures are not proportional");
                    return false;
                }
            }

            int columns = imageA.getWidth();
            int rows = imageA.getHeight();

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    int rgb = imageA.getRGB(col, row);

                    int colCoef = Math.round(col * coefficient);
                    int rowCoef = Math.round(row * coefficient);
                    int rgb2 = imageB.getRGB(colCoef, rowCoef);
                    countPixels++;
                    if (rgb != rgb2) {
                        countFalse++;
                    }
                }
            }

        } catch (Exception e) {
            Logger.getInstance().error("Failed to compare image files ...");
        }
        percentage = 1 - countFalse / countPixels;
        Logger.getInstance().error("Similarity percentage " + String.format("%.2f", percentage * ONE_HUNDRED) + " %");
        return percentage > ALLOWABLE_MATCH_PERCENTAGE / ONE_HUNDRED;
    }


    public static void compareImageOpenCV(String fileA, String fileB) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread(fileA);
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        //CvUtilsFX.showImage(img, "Original");
        Mat img2 = Imgcodecs.imread(fileB);
        //CvUtilsFX.showImage(img, "Sample");
        Mat result = new Mat();
        Imgproc.matchTemplate(img, img2, result, Imgproc.TM_SQDIFF);
        Core.MinMaxLocResult r = Core.minMaxLoc(result);
        System.out.println(r.minVal + " " + r.minLoc);
/*
        Imgproc.rectangle(img, r.minLoc, new Point(r.minLoc.x + img2.width() - 1,
                r.minLoc.y + img2.height() - 1), CvUtils.COLOR_WHITE);
*/
    }
}
