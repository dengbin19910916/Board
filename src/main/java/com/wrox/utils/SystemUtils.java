package com.wrox.utils;

import java.io.File;

/**
 * 系统工具箱
 *
 * Created by Dengbin on 2015/9/28.
 */
public final class SystemUtils {

    /**
     * 操作系统类型
     */
    public enum OSType {
        WINDOWS(File.separator, "[a-zA-Z]:(((\\\\)|/)\\w+)(\\2\\w+)*(\\.\\w+)?"),
        LINUX(File.separator, "/(\\w+/)*\\w+(\\.\\w+)"),
        UNIX(File.separator, "/(\\w+/)*\\w+(\\.\\w+)"),
        MAC(File.separator, "/(\\w+/)*\\w+(\\.\\w+)");

        /**
         * 系统路径的分隔符
         */
        private String separator;

        /**
         * 系统路径的正则表达式
         */
        private String pathRegex;

        OSType(String separator, String pathRegex) {
            this.separator = separator;
            this.pathRegex = pathRegex;
        }

        /**
         * 返回操作系统对应的路径分隔符。
         *
         * @return 与操作系统相关的路径分隔符
         */
        public String getSeparator() {
            return separator;
        }

        /**
         * 返回操作系统对应的绝对路径的正则表达式。
         *
         * @return 绝对路径的正则表达式
         */
        public String getPathRegex() {
            return pathRegex;
        }
    }

    /**
     * 返回操作系统类型
     *
     * @return 操作系统类型
     */
    public static OSType getOSType() {
        String osName = System.getProperty("os.name").split("[ ]+")[0].toUpperCase();
         return OSType.valueOf(osName);
    }

    /**
     * 返回操作系统对应的绝对路径的正则表达式。
     *
     * @return 绝对路径的正则表达式
     */
    public static String getPathRegex() {
        return SystemUtils.getOSType().pathRegex;
    }

    /**
     * 返回操作系统对应的路径分隔符。
     *
     * @return 与操作系统相关的路径分隔符
     */
    public static String getSeparator() {
        return SystemUtils.getOSType().separator;
    }
}
