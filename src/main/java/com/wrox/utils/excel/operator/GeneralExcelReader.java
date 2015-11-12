package com.wrox.utils.excel.operator;


import java.util.List;
import java.util.Map;

/**
 * 当Excel工作簿的数据量不多时使用对象方式处理。
 *
 * Created by dengb on 2015/11/5.
 */
public class GeneralExcelReader implements ExcelReader {


    @Override
    public List<Map<String, String>> readSheets(int... sheetIndex) {
        return null;
    }
}
