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
     * 将Excel数据匹配到Java对象中。<br/>
     * 匹配方法是通过列名进行匹配，例如：
     * 表头：A    - 姓名
     * 字典：姓名 - name
     * 内容：A    - 张三
     * <br/>
     * 需要注意的是参数contents中包含表头数据，在进行数据匹配时需要先过滤掉表头数据再进行匹配。
     *
     * @param clazz    Java对象类型。
     * @param contents 需要进行匹配的数据。
     * @param <T>      Java对象类型，用来强制转换数组类型。
     * @return Java对象数组
     */
    <T> T[] match(Class<T> clazz, List<Map<String, String>> contents);
}
