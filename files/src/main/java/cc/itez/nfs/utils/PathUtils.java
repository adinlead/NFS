/*
 * Copyright (c) 2007, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package cc.itez.nfs.utils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

/**
 * 该类仅包含静态方法，通过将路径字符串或{@link URI}转换为 {@link Path} 来返回。
 *
 * @apiNote 建议使用{@code Path.of}方法获取 {@code Path} 而不是通过此类中定义的 {@code get} 方法获取，
 * 因为在将来的版本中，该类可能会被弃用。
 * @see java.nio.file.Paths
 * @see Path
 * @since 1.7
 */
public final class PathUtils {
    public static final Path ROOT = Path.of("/");

    private PathUtils() {
    }

    /**
     * 将路径字符串或一系列字符串（连接后形成路径字符串）转换为 {@code Path}。
     *
     * @param first 路径字符串或路径字符串的初始部分
     * @param more  要连接以形成路径字符串的附加字符串
     * @return 生成的 {@code Path}
     * @throws InvalidPathException 如果无法将路径字符串转换为 {@code Path}
     * @implSpec 该方法只是使用给定参数调用{@link Path#of(String, String...) Path.of(String, String...)}。
     * @see FileSystem#getPath
     * @see Path#of(String, String...)
     */
    public static Path get(String first, String... more) {
        return Path.of(first, more);
    }

    /**
     * 将给定的 URI 转换为{@link Path}对象。
     *
     * @param uri 要转换的 URI
     * @return 生成的 {@code Path}
     * @throws IllegalArgumentException    如果 {@code uri} 参数上的前置条件不成立。URI 格式是提供程序特定的。
     * @throws FileSystemNotFoundException 如果由 URI 标识的文件系统不存在且无法自动创建，或者由 URI 的方案组件标识的提供程序未安装
     * @throws SecurityException           如果安装了安全管理器并且它拒绝未指定的权限访问文件系统
     * @implSpec 该方法只是使用给定参数调用 {@link Path#of(URI) Path.of(URI)}。
     * @see Path#of(URI)
     */
    public static Path get(URI uri) {
        return Path.of(uri);
    }

    /**
     * 复制Path对象。
     *
     * @param path 要复制的Path对象
     * @return 生成的 {@code Path}
     * @throws IllegalArgumentException    如果 {@code uri} 参数上的前置条件不成立。URI 格式是提供程序特定的。
     * @throws FileSystemNotFoundException 如果由 URI 标识的文件系统不存在且无法自动创建，或者由 URI 的方案组件标识的提供程序未安装
     * @throws SecurityException           如果安装了安全管理器并且它拒绝未指定的权限访问文件系统
     * @implSpec 该方法只是使用给定参数调用 {@link Path#of(URI) Path.of(URI)}。
     * @see Path#of(URI)
     */
    public static Path copy(Path path) {
        return Path.of(path.toUri());
    }

    /**
     * 拼接多个路径
     * 注意，此方法会将more中的根目录拼接到初始路径后面
     *
     * @param base 初始路径
     * @param last 附加路径
     * @return 拼接后的路径
     */
    public static Path join(Object base, Path last) {
        if (last != null) {
            return Path.of(base.toString(), last.toString());
        } else if (base instanceof Path) {
            return ((Path) base).resolve("");
        } else if (base instanceof String) {
            return Path.of((String) base);
        } else {
            return Path.of(base.toString());
        }
    }

    /**
     * 拼接多个路径
     * 注意，此方法会将more中的根目录拼接到初始路径后面
     *
     * @param base 初始路径
     * @param more 要连接以形成路径字符串的附加路径
     * @return 拼接后的路径
     */
    public static Path join(Path base, Path... more) {
        if (more != null) {
            String[] paths = new String[more.length];
            for (int i = 0; i < more.length; i++) {
                if (more[i] == null) {
                    paths[i] = "";
                } else {
                    paths[i] = more[i].toString();
                }
            }
            return Path.of(base.toString(), paths);
        } else {
            return base.resolve("");
        }
    }

    /**
     * 递归创建目录
     * 如果路径不存在则创建
     *
     * @param path 路径
     */
    public static void mkdirs(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                path.toFile().mkdirs();
            }
        }
    }
}
