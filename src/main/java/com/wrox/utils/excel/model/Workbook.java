package com.wrox.utils.excel.model;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dengbin on 2015/10/12.
 */
public class Workbook {

    private org.apache.poi.ss.usermodel.Workbook workbook;

    public Workbook(InputStream inputStream) throws IOException, InvalidFormatException {
        workbook = WorkbookFactory.create(inputStream);
    }

}
