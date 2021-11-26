package Utils;

import aquality.selenium.core.logging.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PhotoUtils {
    public static boolean compareImage(File fileA, File fileB) {
        float percentage;
        float countFalse = 0;
        float countPixels = 0;
        float coefficient = 1;
        try {
            BufferedImage imageA = ImageIO.read(fileA);
            BufferedImage imageB = ImageIO.read(fileB);

            if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight()) {
                coefficient = roundAvoid((float) imageA.getWidth() / imageB.getWidth(), 2);
                if (coefficient != roundAvoid((float) imageA.getHeight() / imageB.getHeight(), 2)) {
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
        if (percentage > 0.7) {
            return true;
        } else {
            return false;
        }
    }

    public static float roundAvoid(float value, int places) {
        float scale = (float) Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
