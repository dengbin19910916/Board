package com.wrox;

import com.wrox.utils.excel.Book;
import com.wrox.utils.excel.ExcelType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;

/**
 * @author dengb
 */
public class Demo {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        InputStream is = new FileInputStream(Test.clerk);
        PushbackInputStream[] ps = new PushbackInputStream[1];
        Book book = new Book();
        ExcelType excelType = ExcelType.getExcelType(is, ps, book);
        System.out.println(excelType + ", " + Arrays.toString(ps) + ", " + book);
//        byte[] bytes = new byte[8];
//        is.read(bytes);
//        System.out.println(Arrays.toString(bytes));

        Workbook wb = WorkbookFactory.create(ps[0]);
        System.out.println(wb);
    }

    public static void mark() throws IOException {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(new FileInputStream(Test.root + "is.txt"));

        byte[] array = new byte[2];

        int tmp = 0;
        int count = 0;

        while ((count = pushbackInputStream.read(array)) != -1) {
            //两个字节转换为整数
            tmp = (short) ((array[0] << 8) | (array[1] & 0xff));
            tmp = tmp & 0xFFFF;

            //判断是否为BIG5，如果是则显示BIG5中文字
            if (tmp >= 0xA440 && tmp < 0xFFFF) {
                System.out.println("BIG5:" + new String(array));
            } else {
                //将第二个字节推回流
                pushbackInputStream.unread(array, 1, 1);
                //显示ASCII范围的字符
                System.out.println("ASCII: " + (char) array[0]);
            }
        }
        pushbackInputStream.close();
        
        
        
       
    }
}
