package com.wrox.utils.excel.operator;

import java.util.List;

/**
 * Excel工作簿的行数据处理逻辑。
 *
 * Created by dengb on 2015/10/28.
 * @author dengb
 */
public interface RowHandler {
    /**
     * 处理每一行的数据
     *
     * @param curRow
     * @param rows
     * @throws ExcelParseException
     */
    void getRows(int curRow, List<String> rows) throws ExcelParseException;
}
