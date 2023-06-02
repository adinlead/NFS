package cc.itez.nfs.utils;

import java.io.File;

public class FileUtils {
    public static void delete(File file, boolean recursive) {
        if (file.exists()) {
            if (file.isFile()) {
                if (file.delete()) {
                    return;
                }
            }
            if (file.isDirectory()){
                File[] files = file.listFiles();
            }
        }
    }

    public static boolean mkdirs(File file) {
        return (file.exists() && file.isDirectory()) || file.mkdirs();
    }
}
