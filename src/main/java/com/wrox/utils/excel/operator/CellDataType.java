package com.wrox.utils.excel.operator;

/**
 * 用事件解析Excel文件时用来确定单元格的数据类型。
 *
 * Created by dengb on 2015/10/28.
 * @author dengb
 */
public enum CellDataType {

    /**
     * 单元格的数据为Boolean类型。
     */
    BOOL,
    /**
     * 单元格数据错误。
     */
    ERROR,
    /**
     * 单元格的数据为公式，需要进行计算才能获取最终的数据。
     */
    FORMULA,
    /**
     * 单元格的数据为内联字符串。
     */
    INLINE_STR,
    /**
     * 单元格的数据为字符串，数据存储在共享字符串表中。值为共享字符串表中的索引。
     */
    SST_INDEX,
    /**
     * 单元格的数据为数值类型。
     */
    NUMERIC,
    /**
     * 单元格的数据为日期类型。
     */
    DATE,
    /**
     * 单元格的数据为时间类型。
     */
    TIME,
    /**
     * 单元格的数据为时间戳类型。
     */
    DATETIME,
    /**
     * 单元格的数据为空。
     */
    NULL
}
