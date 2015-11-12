package com.wrox;

import com.wrox.utils.excel.Book;
import com.wrox.utils.excel.Credit;
import com.wrox.utils.excel.operator.Excels;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by dengb on 2015/9/10.
 */
public class Test {

    private static final String root = Test.class.getResource("/").getPath() +"../../file/";

    public static final File clerk = new File(root + "行员管理.xlsx");
    public static final File clerk2 = new File(root + "行员管理.xls");

    public static final File book = new File(root + "书籍管理.xlsx");

    public static final File ftp = new File(root + "客户经理.xlsx");
    public static final File credit = new File(root + "信贷数据.xlsx");
    public static final File simple = new File(root + "信贷数据simple.xlsx");
    public static final File simple2 = new File(root + "信贷数据simple2.xlsx");

    public static void main(String[] args) throws OpenXML4JException, SAXException, IOException {
        Excels excels = new Excels.Builder()
                .setLocalDateFormat("yyyy/M/d")
//                .setLocalDateFormat("yyyy-MM-dd")
                .create();
//        Book[] books = excels.read(Book.class, Test.book);
        Credit[] credits = excels.read(Credit.class, Test.simple2);
//        Credit[] credits = excels.read(Credit.class, Test.simple);
//        Clerk[] clerks = excels.read(Clerk.class, Test.clerk);

//        println(books);
        println(credits);
//        println(clerks);

        Book[] books = com.wrox.utils.excel.Excels.read(Book.class, WorkbookFactory.create(book));
        println(books);
    }

    private static void println(Object[] array) {
        int[] is = {0};
        Arrays.asList(array).stream().forEach(data -> System.out.println((++is[0]) + " => " + data));
    }
}
