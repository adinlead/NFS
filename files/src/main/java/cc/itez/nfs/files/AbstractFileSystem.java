package cc.itez.nfs.files;

import cc.itez.nfs.mould.Outcome;
import lombok.NonNull;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

/**
 * 标准文件操作接口
 */
public interface AbstractFileSystem {
    /**
     * 列出指定路径下的文件
     *
     * @param path 路径
     * @param all  全部文件
     * @return 文件列表
     */
    @NonNull
    List<Path> ls(@NonNull Path path, boolean all) throws FileNotFoundException;

    /**
     * 列出指定路径下的文件
     *
     * @param path 路径
     * @return 文件列表
     */
    @NonNull
    default List<Path> ls(@NonNull Path path) throws FileNotFoundException {
        return this.ls(path, false);
    }

    /**
     * 返回指定路径的文件
     *
     * @param path 路径
     * @return 与之对应的文件，如果路径不存在，则应当返回空
     */
    @NonNull
    File get(@NonNull Path path) throws FileNotFoundException;

    /**
     * 创建目录
     *
     * @param path 路径
     * @return 处理结果
     */
    @NonNull
    Outcome mkdirs(@NonNull Path path);

    /**
     * 删除目标
     *
     * @param path      目标路径
     * @param recursive 是否递归删除目录及其内容，如果此项为false，那么如果目录中有文件将无法删除
     * @return 处理结果
     */
    @NonNull
    Outcome rm(@NonNull Path path, boolean recursive);

    /**
     * 删除目录
     *
     * @param path 路径
     * @return 处理结果
     */
    @NonNull
    default Outcome rm(@NonNull Path path) {
        return this.rm(path, true);
    }

    /**
     * 保存文件,如果目标文件存在则终止操作
     *
     * @param source 源文件
     * @param target 目标路径
     * @return 处理结果
     */
    @NonNull
    default Outcome save(@NonNull File source, @NonNull Path target) throws IOException {
        return this.save(source, target, false);
    }

    /**
     * 保存文件
     *
     * @param source 源文件
     * @param target 目标路径
     * @param cover  如果目标存在是否覆盖
     * @return 处理结果
     */
    @NonNull
    default Outcome save(@NonNull File source, @NonNull Path target, boolean cover) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(source)) {
            return this.save(inputStream, target, cover);
        }
    }

    /**
     * 将流保存为文件,如果目标文件存在则终止操作
     *
     * @param source 源文件流
     * @param target 目标路径
     * @return 处理结果
     */
    @NonNull
    default Outcome save(@NonNull InputStream source, @NonNull Path target) throws IOException {
        return this.save(source, target, false);
    }

    /**
     * 将流保存为文件
     *
     * @param source 源文件流
     * @param target 目标路径
     * @param cover  如果目标存在是否覆盖
     * @return 处理结果
     */
    @NonNull
    Outcome save(@NonNull InputStream source, @NonNull Path target, boolean cover) throws IOException;


    /**
     * 复制文件,如果目标文件存在则终止操作
     *
     * @param source 源文件路径
     * @param target 目标路径
     * @return 处理结果
     */
    default Outcome cp(@NonNull Path source, @NonNull Path target) throws IOException {
        return this.cp(source, target, false);
    }

    /**
     * 复制文件
     *
     * @param source 源文件路径
     * @param target 目标路径
     * @param cover  如果目标存在是否覆盖
     * @return 处理结果
     */
    Outcome cp(@NonNull Path source, @NonNull Path target, boolean cover) throws IOException;

    /**
     * 移动文件,如果目标文件存在则终止操作
     *
     * @param source 源文件路径
     * @param target 目标路径
     * @return 处理结果
     */
    @NonNull
    default Outcome mv(@NonNull Path source, @NonNull Path target) throws IOException {
        return this.mv(source, target, false);
    }

    /**
     * 移动文件
     *
     * @param source 源文件路径
     * @param target 目标路径
     * @param cover  如果目标存在是否覆盖
     * @return 处理结果
     */
    @NonNull
    Outcome mv(@NonNull Path source, @NonNull Path target, boolean cover) throws IOException;

    /**
     * 搜索文件
     *
     * @param base  搜索目录
     * @param name  文件名
     * @param type  文件类型
     * @param level 搜索深度(-1为无限,0为目标本身)
     * @return 匹配的文件列表
     */
    @NonNull
    List<Path> find(@NonNull Path base, @NonNull String name, @NonNull FileType type, int level);

    /**
     * 搜索文件
     *
     * @param base 搜索目录
     * @param name 文件名
     * @return 匹配的文件列表
     */
    @NonNull
    default List<Path> find(@NonNull Path base, @NonNull String name) {
        return this.find(base, name, FileType.ALL, -1);
    }

    enum FileType {
        ALL,
        FILE,
        DIR
    }
}
