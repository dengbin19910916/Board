package com.wrox.utils.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;

/**
 * Excel文件类型
 *
 * Created by Dengbin on 2015/10/11.
 * @author Dengbin
 */
public enum ExcelType {
    /**
     * Excel 97-2003 工作簿
     */
    XLS,
    /**
     * Excel 工作簿
     */
    XLSX ,
    /**
     * Excel启用宏的工作簿
     */
    XLSM,
    /**
     * Excel 二进制工作簿
     */
    XLSB,
    /**
     * Excel 97-2003 模板
     */
    XLT,
    /**
     * Excel 模板
     */
    XLTX,
    /**
     * Excel 启用宏的模板
     */
    XLTM ;

    /**
     * 返回Excel文件的类型。
     *
     * @return Excel文件的类型。
     */
    public static ExcelType getExcelType(InputStream is, PushbackInputStream[] pin, Book book) throws IOException, InvalidFormatException {
        PushbackInputStream pis = new PushbackInputStream(is, 8);
        byte[] array = new byte[8];
        pis.read(array);
        System.out.println(Arrays.toString(array));
//        is.reset();
        pis.unread(array, 0, 8);

        pin[0] = pis;

//        Workbook wb = WorkbookFactory.create(pis);
//        System.out.println(wb);
        return null;

//        throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
    }

     /*
    // If clearly doesn't do mark/reset, wrap up
        if (! inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }

        // Ensure that there is at least some data there
        byte[] header8 = IOUtils.peekFirst8Bytes(inp);

        // Try to create
        if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
            NPOIFSFileSystem fs = new NPOIFSFileSystem(inp);
            return create(fs, password);
        }
        if (POIXMLDocument.hasOOXMLHeader(inp)) {
            return new XSSFWorkbook(OPCPackage.open(inp));
        }
        throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");

     */
}
