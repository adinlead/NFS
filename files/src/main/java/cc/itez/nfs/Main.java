package cc.itez.nfs;


import cc.itez.nfs.utils.PathUtils;
import cc.itez.nfs.utils.UnsafeUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("============= 0");
        Files.walk(Path.of("/home/adinlead/temp"),0).forEach(System.out::println);
        System.out.println("============= 1");
        Files.walk(Path.of("/home/adinlead/temp"),1).forEach(System.out::println);
        System.out.println("============= 2");
        Files.walk(Path.of("/home/adinlead/temp"),2).forEach(System.out::println);
    }
}