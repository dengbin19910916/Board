package com.wrox.utils;

/**
 * 系统工具箱
 *
 * Created by Dengbin on 2015/9/28.
 */
public final class SystemUtils {

    public enum OSType {
        WINDOWS,
        LINUX,
        UNIX,
        MAC
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
