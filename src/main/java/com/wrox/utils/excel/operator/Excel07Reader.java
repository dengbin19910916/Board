package com.wrox.utils.excel.operator;

import org.apache.poi.xssf.model.SharedStringsTable;

import java.util.List;

/**
 * Created by dengb on 2015/10/28.
 */
public class Excel07Reader {

    /**
     * 共享字符串表。
     */
    private SharedStringsTable sst;
    /**
     * 上一次的内容。
     */
    private String lastContents;
    /**
     * 字符串标识。
     */
    private boolean nextIsString;
    /**
     * 工作表索引。
     */
    private int sheetIndex;
    /**
     * 行集合。
     */
    private List<String> rowList;
    /**
     * 当前行。
     */
    private int currentRow;
    /**
     * 当前列。
     */
    private int currentColumn;
    /**
     * T元素标识，对应Excel中的T公式。
     */
    private boolean isTElement;

}
