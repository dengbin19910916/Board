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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统文件处理工具。
 *
 * Created by Dengbin on 2015/9/27.
 */
public final class FileUtils {

    private static final Logger log = LogManager.getLogger();

    public enum PathType {
        WINDOWS("[a-zA-Z]:(((\\\\)|/)\\w+)(\\2\\w+)*(\\.\\w+)?"),
        LINUX("/(\\w+/)*\\w+(\\.\\w+)");

        private String regex;

        PathType(String regex) {
            this.regex = regex;
        }

        @Override
        public String toString() {
            return this.regex;
        }
    }

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
     * 创建文件。<br/>
     * 文件中不能包含非法字符" < ", ">", " "，<br/>
     * 父子路径可以使用" . "， " / "， " \ "， " | "， " : "， " " "， " * "， " ? "分隔。<br/>
     * 例如：<br/>
     * scott.ticket.attachment.a.txt => %BASE_PATH%\scott\ticket\attachment\a.txt
     *
     * @param filepath 文件的绝对路径
     * @param contents 文件内容
     */
    public static void createFile(String filepath, byte[] contents) {
        filepath = filepath.replaceAll("[<> ]", "");
        String[] ds = filepath.split("[./\\\\|:\"*?]").clone(); // problem : 需要截取出文件名。

        String regex = PathType.valueOf(SystemUtils.getOSType().toString()).toString();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filepath);
        if (matcher.matches()) {    // 是绝对路径
            Path file = Paths.get(filepath);    // 文件
            Path parent = file.getParent();     // 文件所在目录

            if (!Files.exists(parent)) {
                try {
                    Files.createDirectories(parent);
                } catch (IOException ignored) {
                }
            }

            try {
                Files.deleteIfExists(file);
                Files.createFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String root = getBasePath();

        }

        createFile(Paths.get(filepath), contents);
    }

    /**
     * 创建文件。
     *
     * @param filepath 文件路径对象
     * @param contents 文件内容
     */
    public static void createFile(Path filepath, byte[] contents) {
        Path path = null;

        if (Files.exists(filepath)) {
            try {
                Files.delete(filepath);
                log.info("删除同名文件[{}]。", filepath.getFileName());
            } catch (IOException e) {
                log.warn("删除文件失败！", e);
            }
        }
        try {
            path = Files.createFile(filepath);
            log.info("文件{}已被创建[{}]。", filepath.getFileName());
        } catch (IOException e) {
            log.warn("创建文件失败！", e);
        }
        writeFile(path, contents);
    }

    /**
     * 创建文件。
     *
     * @param directory 所属文件夹
     * @param filename 文件名称
     * @param contents 文件内容
     */
    public static void createFile(String directory, String filename, byte[] contents) {
        createFile(getDirectoryPath(directory), filename, contents);
    }

    /**
     * 创建文件。
     *
     * @param directory 所属文件夹
     * @param filename 文件名称
     * @param contents 文件内容
     */
    public static void createFile(Path directory, String filename, byte[] contents) {   // root
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                log.warn(String.format("创建上传根路径[%s]失败！", directory.getFileName()), e);
            }
        }
        createFile(Paths.get(directory.toString(), filename), contents);
    }

    /**
     * 向文件写入内容。
     *
     * @param file 文件路径
     * @param contents 文件内容
     */
    private static void writeFile(Path file, byte[] contents) {
        try (FileOutputStream outputStream = new FileOutputStream(file.toString());
             FileChannel channel = outputStream.getChannel()) {
            ByteBuffer.allocate(contents.length > 20 * 1024 ? 20 * 1024 : contents.length);
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

        return Paths.get(getBasePath(), dirs);
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
