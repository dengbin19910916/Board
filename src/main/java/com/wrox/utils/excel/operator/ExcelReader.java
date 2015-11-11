package com.wrox.utils.excel.operator;

import java.util.List;
import java.util.Map;

/**
 * Excel工作簿的读取接口，定义了Excel工作簿的共同的读取方法。
 *
 * @author dengb
 * @version 1.0
 */
public interface ExcelReader {

    /**
     * 读取表单的内容。
     *
     * @return 表单内容的集合。
     */
    List<Map<String, String>> readSheets(int... sheetIndex);
}
