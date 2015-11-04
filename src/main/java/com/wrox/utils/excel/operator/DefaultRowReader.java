package com.wrox.utils.excel.operator;

import java.util.List;

/**
 * Created by dengb on 2015/10/27.
 */
public class DefaultRowReader implements RowReader {
    @Override
    public void getRows(int sheetIndex, int currentRow, List<String> rows) {

    }

    @Override
    public void getRows(int currentRow, List<String> rows) throws ExcelParseException {
        System.out.println("111");
    }
}
