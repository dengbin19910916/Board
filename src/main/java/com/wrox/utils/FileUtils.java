package com.wrox.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 系统路径工具。
 *
 * Created by Dengbin on 2015/9/27.
 */
public final class FileUtils {

    private static final Logger log = LogManager.getLogger();

    /**
     * 返回系统的文件（夹）路径。
     * 路径获取顺序为：
     * 1.读取数据库SYSTEM_CONFIG中的PATH
     * 2.读取配置文件system_config.properties中的path
     * 3.获取服务器路径
     *
     * @param path 文件名/文件夹名
     * @return 文件路径/文件夹路径
     */
    public static Path getPath(String path) {
        Path serverPath = Paths.get(System.getProperty("user.dir"), "..");
        return null;
    }

    /**
     * 创建文件。
     *
     * @param directory 所属文件夹
     * @param filename 文件名称
     */
    public static void createFile(Path directory, String filename, byte[] contents) {
        Path path = null;

        Path filePath = Paths.get(directory.toString(), filename);
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
                log.info("删除同名文件[{}]。", directory.toAbsolutePath());
            } catch (IOException e) {
                log.warn("删除文件失败！", e);
            }
        }
        try {
            path = Files.createFile(filePath);
            log.info("文件{}已被创建[{}]。", filename, filePath.toAbsolutePath());
        } catch (IOException e) {
            log.warn("创建文件失败！", e);
        }
        writeFile(path, contents);
    }

    private static void writeFile(Path file, byte[] contents) {
        try (FileOutputStream outputStream = new FileOutputStream(file.toString());
             FileChannel channel = outputStream.getChannel()) {
            ByteBuffer.allocate(contents.length);
            ByteBuffer buffer = ByteBuffer.wrap(contents);
            outputStream.flush();
            channel.write(buffer);
        } catch (FileNotFoundException e) {
            log.warn("输出流未打开！", e);
        } catch (IOException e) {
            log.warn("输出流刷新异常！", e);
        }
    }

    /**
     * 返回文件夹路径。
     *
     * @param directory 文件夹路径名，可以用“.”， “/”， “\”， “|”， “:”， “"”， “*”， “?”分隔，非法符号将被去除。
     * @return 文件夹路径
     */
    public static Path getDirectoryPath(String directory) {
        directory = directory.replaceAll("[<> ]", "");
        String[] dirs = directory.split("[./\\\\|:\"*?]");
        Path path = Paths.get(getBasePath(), dirs);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.warn(String.format("创建上传根路径[%s]失败！", path.toAbsolutePath()), e);
            }
        }

        return path;
    }

    /**
     * 返回系统路径
     *
     * @return 系统路径
     */
    private static String getBasePath() {
        return StringUtils.isBlank(getBasePathByDatabase()) ?
                StringUtils.isBlank(getBasePathByConfigurationFile()) ? getDefaultBasePath() : getBasePathByConfigurationFile() :
                getBasePathByDatabase();
    }

    /**
     * 返回默认的系统路径。
     *
     * @return 系统路径
     */
    private static String getDefaultBasePath() {
        return System.getProperty("user.dir") + "\\..\\board";
    }

    /**
     * 返回数据库中的系统路径。
     *
     * @return 系统路径
     */
    private static String getBasePathByDatabase() {
        return "";
    }

    /**
     * 返回数配置文件中的系统路径。
     *
     * @return 系统路径
     */
    private static String getBasePathByConfigurationFile() {
        return "";
    }

    public enum SystemPath {
        UPLOAD("upload");

        private String path;

        SystemPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return System.getProperty("user.dir") + "..\\" + this.path;
        }
    }
}
