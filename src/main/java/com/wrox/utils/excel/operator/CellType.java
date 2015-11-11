package com.wrox.utils.excel.operator;

/**
 * 单元格数据类型。
 *
 * @author dengb
 * @version 1.0
 */
public enum CellType {

    /**
     * 单元格的数据为数值类型。
     */
    NUMERIC,
    /**
     * 单元格的数据为内联数据。
     */
    INLINE,
    /**
     * 单元格的数据为字符串，数据存储在共享字符串表中。值为共享字符串表中的索引。
     */
    STRING,
    /**
     * 单元格的数据为公式，需要进行计算才能获取最终的数据。
     */
    FORMULA,
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
    BLANK,
    /**
     * 单元格的数据为Boolean类型。
     */
    BOOLEAN,
    /**
     * 单元格数据错误。
     */
    ERROR,
    /**
     * 单元格数据为常规类型。
     */
    GENERAL,
    /**
     * 单元格数据类型未知。
     */
    UNKNOWN
}
