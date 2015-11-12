package com.wrox;

import com.wrox.utils.excel.Book;
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

    public static final String clerk = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\行员管理.xlsx";
    public static final String clerk2 = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\行员管理.xls";

    public static final String book = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\书籍管理.xlsx";

    public static final String ftp = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\客户经理.xlsx";
    public static final String credit = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\信贷数据.xlsx";
    public static final String simple = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\信贷数据simple.xlsx";
    public static final String simple2 = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\信贷数据simple2.xlsx";

    public static void main(String[] args) throws OpenXML4JException, SAXException, IOException {
        Excels excels = new Excels.Builder()
                .setLocalDateFormat("yyyy/M/d")
//                .setLocalDateFormat("yyyy-MM-dd")
                .create();
//        Book[] books = excels.read(Book.class, new File(Test.book));
//        Credit[] credits = excels.read(Credit.class, new File(Test.simple2));
//        Credit[] credits = excels.read(Credit.class, new File(Test.simple));
//        Clerk[] clerks = excels.read(Clerk.class, new File(Test.clerk));

//        println(books);
//        println(credits);
//        println(clerks);

        Book[] books = com.wrox.utils.excel.Excels.read(Book.class, WorkbookFactory.create(new File(book)));
        println(books);
    }

    private static void println(Object[] array) {
        int[] is = {0};
        Arrays.asList(array).stream().forEach(data -> System.out.println((++is[0]) + " => " + data));
    }
}
