package com.wrox.utils.excel.operator;

import java.util.List;
import java.util.Map;

/**
 * Excel文件数据与对象字段的匹配器。
 * 通过此接口完成Excel文件的数据与Javabean对象的转换。
 *
 * @author dengb
 * @version 1.0
 */
public interface ExcelMapper {

    /**
     * 将Excel数据匹配到Java对象中。
     *
     * @param clazz    Java对象类型。
     * @param contents 需要进行匹配的数据。
     * @param <T>      Java对象类型，用来强制转换数组类型。
     * @return Java对象数组
     */
    <T> T[] match(Class<T> clazz, List<Map<String, String>> contents);
}
