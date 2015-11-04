package com.wrox.utils.excel.operator;

import java.util.List;

/**
 * Created by dengb on 2015/10/26.
 */
public interface RowReader {

    void getRows(int sheetIndex, int currentRow, List<String> rows);

    void getRows(int currentRow, List<String> rows) throws ExcelParseException;
}
