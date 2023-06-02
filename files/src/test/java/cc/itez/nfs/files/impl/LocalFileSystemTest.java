package cc.itez.nfs.files.impl;


import cc.itez.nfs.utils.PathUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class LocalFileSystemTest {
    public static void main(String[] args) throws FileNotFoundException {
        File testSourceFile = new File("/home/adinlead/Pictures/logo2.png");
        long current = System.currentTimeMillis();
        Path testCreate = Paths.get("/test_" + current + ".temp");
        Path testMoveTo = Paths.get("/test_" + current + ".move.temp");
        Path testCopyAs = Paths.get("/test_" + current + ".copy.temp");
        LocalFileSystem fileSystem = new LocalFileSystem("/home/adinlead/temp");
        try {
            fileSystem.save(testSourceFile, testCreate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("新建文件测试通过");
        }
        try {
            fileSystem.mv(testCreate, testMoveTo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("移动文件测试通过");
        }
        try {
            fileSystem.cp(testMoveTo, testCopyAs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("复制文件测试通过");
        }
        for (Path path : fileSystem.find(PathUtils.ROOT, "*.temp")) {
            System.out.println(path);
            System.out.println(fileSystem.rm(path));
        }
        try {
            for (Path path : fileSystem.ls(PathUtils.ROOT)) {
                if (path.equals(testCopyAs)) {
                    System.out.println("找到被复制的文件");
                } else if (path.equals(testMoveTo)) {
                    System.out.println("找到被移动的文件");
                }
                System.out.println("\t ls -> " + path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("列举文件测试通过");
        }
    }
}