package com.wrox.utils.excel.operator;

/**
 * Excel文件解析异常。
 *
 * @author dengb
 * @version 1.0
 */
public class ExcelParseException extends RuntimeException {

    private static final long serialVersionUID = -1461206413385669567L;

    public ExcelParseException() {
        super("Excel文件错误！");
    }

    public ExcelParseException(String msg) {
        super(msg);
    }
}
