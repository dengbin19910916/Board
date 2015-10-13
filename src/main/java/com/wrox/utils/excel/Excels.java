package com.wrox.utils.excel;

import com.wrox.utils.excel.model.Workbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

/**
 * Excel文件工具类。用于读取与创建Excel文件。
 *
 * Created by Dengbin on 2015/10/12.
 */
public final class Excels {

    /**
     * 读取Excel文件中的内容。
     */
    public static Workbook read(InputStream inputStream) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader(WorkbookFactory.create(inputStream));
        reader.readSheetNames();
//        System.out.println(Arrays.toString(reader.readSheetHeaders("城西支行")));
//        Map<String, Object>[] map = reader.readSheetContent("城东支行", Clerk.class);
        Map<String, Object>[] map = reader.readSheetContent("城西支行", Clerk.class);
        System.out.println(Arrays.toString(map));

        return null;
    }

    /**
     * 创建Excel文件，并向其中写入内容。
     */
    public static void wirte() {

    }

}
