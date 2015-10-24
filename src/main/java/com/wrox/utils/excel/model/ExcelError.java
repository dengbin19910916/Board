package com.wrox.utils.excel.model;

import org.apache.poi.ss.util.CellReference;

import java.util.HashMap;

/**
 * 用来存储Excel读取时的错误信息。
 *
 * Created by dengb on 2015/10/18.
 * @author dengb
 */
public class ExcelError extends HashMap<CellReference, String> {
    private static final long serialVersionUID = -4787148310069759478L;

    public ExcelError() {
        super(0);
    }
}
