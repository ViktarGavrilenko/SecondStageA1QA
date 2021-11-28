package Utils;

import java.io.File;

public class FileUtils {
    public static void deleteFile(String pathFile) {
        File file = new File(pathFile);
        file.delete();
    }
}
