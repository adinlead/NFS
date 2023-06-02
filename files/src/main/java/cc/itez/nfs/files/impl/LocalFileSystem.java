package cc.itez.nfs.files.impl;

import cc.itez.nfs.files.AbstractFileSystem;
import cc.itez.nfs.mould.Outcome;
import cc.itez.nfs.utils.FileUtils;
import cc.itez.nfs.utils.IOUtils;
import cc.itez.nfs.utils.PathUtils;
import cc.itez.nfs.utils.StringUtils;
import lombok.NonNull;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 基于本地文件系统实现文件操作
 */
public class LocalFileSystem implements AbstractFileSystem {
    private final Path BASE;

    public LocalFileSystem(Path base) {
        this.BASE = base;
    }

    public LocalFileSystem(File file) {
        this.BASE = file.toPath();
    }

    public LocalFileSystem(String base) {
        this.BASE = Path.of(base);
    }

    /**
     * 转换为绝对路径
     *
     * @param path 路径
     * @return 绝对路径
     */
    private Path realPath(Path path) {
        return PathUtils.join(this.BASE, path);
    }

    /**
     * 转换为相对路径
     *
     * @param path 路径
     * @return 相对路径
     */
    private Path relativePath(Path path) {
        return PathUtils.join(PathUtils.ROOT, BASE.relativize(path.normalize()));
    }

    /**
     * 检查目标文件是否存在并返回绝对路径
     *
     * @param target 目标文件路径
     * @param cover  是否覆盖
     * @return 目标问卷绝对路径
     * @throws FileAlreadyExistsException 当目标路径存在且不采取覆盖策略时抛出
     * @throws IOException                其他情况抛出
     */
    private Path checkTargetFile(Path target, boolean cover) throws IOException {
        target = this.realPath(target);
        if (Files.exists(target)) {
            if (!cover) {
                throw new FileAlreadyExistsException(target + " (Target file already exists)");
            }
        } else {
            try {
                Files.createDirectories(target.getParent());
            } catch (IOException e) {
                if (!FileUtils.mkdirs(target.toFile().getParentFile())) {
                    throw e;
                }
            }
        }
        return target;
    }

    /**
     * 检查源文件是否存在并返回绝对路径
     *
     * @param source 源文件路径
     * @return 目标问卷绝对路径
     * @throws FileAlreadyExistsException 当目标路径存在且不采取覆盖策略时抛出
     * @throws IOException                其他情况抛出
     */
    private Path checkSourceFile(Path source) throws IOException {
        source = this.realPath(source);
        if (!Files.exists(source)) {
            throw new FileNotFoundException(source + " (Source file not exists)");
        }
        return source;
    }


    @Override
    public @NonNull List<Path> ls(@NonNull Path path, boolean all) throws FileNotFoundException {
        if (!Files.exists(path)) {
            throw new FileNotFoundException(path + " (No such file or directory)");
        }
        List<Path> result = new LinkedList<>();
        File file = this.realPath(path).toFile();
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    if (all) {
                        for (File f : files) {
                            result.add(this.relativePath(f.toPath()));
                        }
                    } else {
                        for (File f : files) {
                            if (!f.isHidden()) {
                                result.add(this.relativePath(f.toPath()));
                            }
                        }
                    }
                }
            } else {
                result.add(path);
            }
        }
        return result;
    }

    @Override
    public @NonNull File get(@NonNull Path path) {
        return PathUtils.join(this.BASE, path).toFile();
    }

    @Override
    public @NonNull Outcome mkdirs(@NonNull Path path) {
        path = this.realPath(path);
        File file = path.toFile();
        if (FileUtils.mkdirs(file)) {
            return Outcome.success(path);
        }
        return Outcome.failure("Create directory failure!");
    }

    @Override
    public @NonNull Outcome rm(@NonNull Path path, boolean recursive) {
        path = this.realPath(path);
        try {
            if (recursive) {
                try (Stream<Path> walk = Files.walk(path)) {
                    walk.sorted((p1, p2) -> -p1.compareTo(p2)).forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException ignored) {
                        }
                    });
                }
            } else {
                Files.deleteIfExists(path);
            }
            return Outcome.success(path);
        } catch (IOException e) {
            return Outcome.error(e);
        }
    }

    @Override
    public @NonNull Outcome save(@NonNull InputStream source, @NonNull Path target, boolean cover) throws IOException {
        target = this.realPath(target);
        File file = target.toFile();
        if (file.exists()) {
            if (!cover) {
                throw new FileAlreadyExistsException(target.toString());
            }
        } else {
            FileUtils.mkdirs(file.getParentFile());
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(source, outputStream);
        }
        return Outcome.success(target);
    }

    @Override
    public Outcome cp(@NonNull Path source, @NonNull Path target, boolean cover) throws IOException {
        target = this.checkTargetFile(target, cover);
        source = this.checkSourceFile(source);
        Files.copy(source, target);
        return Outcome.success(target);
    }

    @Override
    public Outcome mv(@NonNull Path source, @NonNull Path target, boolean cover) throws IOException {
        target = this.checkTargetFile(target, cover);
        source = this.checkSourceFile(source);
        Files.move(source, target);
        return Outcome.success(target);
    }

    @Override
    public @NonNull List<Path> find(@NonNull Path base, @NonNull String name, @NonNull FileType type, int level) {
        base = this.realPath(base);
        List<Path> result = new LinkedList<>();
        if (Files.exists(base)) {
            Function<String, Boolean> nameFilter;
            if (StringUtils.isEmpty(name)) {
                nameFilter = (s) -> true;
            } else if (name.contains("*")) {
                nameFilter = new Function<>() {
                    final String nameRegex = name.replace(".", "\\.")
                            .replace("*", ".*")
                            .replace("?", ".");

                    @Override
                    public Boolean apply(String s) {
                        if (StringUtils.isEmpty(s)) {
                            return false;
                        }
                        return s.matches(nameRegex);
                    }
                };
            } else {
                nameFilter = name::equals;
            }
            Function<File, Path> typeFilter = switch (type) {
                case DIR -> (f) -> {
                    if (f.exists() && f.isDirectory() && nameFilter.apply(f.getName())) {
                        return this.relativePath(f.toPath());
                    }
                    return null;
                };
                case FILE -> (f) -> {
                    if (f.exists() && f.isFile() && nameFilter.apply(f.getName())) {
                        return this.relativePath(f.toPath());
                    }
                    return null;
                };
                default -> (f) -> {
                    if (f.exists() && nameFilter.apply(f.getName())) {
                        return this.relativePath(f.toPath());
                    }
                    return null;
                };
            };
            if (level < 0) {
                level = Integer.MAX_VALUE;
            }
            try (Stream<Path> walk = Files.walk(base, level)) {
                walk.forEach(path -> {
                    Path p = typeFilter.apply(path.toFile());
                    if (p != null) {
                        result.add(p);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
