package com.wrox.utils;

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
        WINDOWS("\\"),
        LINUX("/"),
        UNIX("/"),
        MAC("/");

        /**
         * 系统路径分隔符
         */
        private String separator;

        OSType(String separator) {
            this.separator = separator;
        }

        /**
         * 返回系统路径分隔符。
         *
         * @return 系统路径分隔符
         */
        public String getSeparator() {
            return separator;
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
}
