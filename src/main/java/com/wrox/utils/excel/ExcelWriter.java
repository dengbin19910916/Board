package com.wrox.utils.excel;

import com.wrox.utils.excel.annotation.ExcelSheet;
import com.wrox.utils.excel.annotation.ExcelWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by dengb on 2015/10/22.
 */
public class ExcelWriter {

    public ExcelWriter() {
        super();
    }

    public <T> void write(Class<T> clazz, File file, T[] contents) throws IOException {
        String dir = "C:\\WorkSpace\\";
        ExcelWorkbook annotation = clazz.getAnnotation(ExcelWorkbook.class);
        String excelName = annotation.value();
        ExcelSheet[] excelSheets = annotation.sheets();
        String[] sheetsName = new String[excelSheets.length];
        final int[] index = {0};
        Arrays.asList(excelSheets).stream().forEach(sheet -> sheetsName[index[0]++] = sheet.value());
        String excelType = annotation.type().toString().toLowerCase();

        Workbook workbook = new XSSFWorkbook();
        workbook.write(new FileOutputStream(Paths.get(dir, excelName + "." + excelType).toFile()));
        System.out.println("Write => " + Arrays.toString(contents));
    }

    public <T> void write(Class<T> clazz, Path path, T[] contents) throws IOException {

    }

    public <T> void write(Class<T> clazz, T[] contents) throws IOException {
        String dir = "C:\\WorkSpace\\";
        ExcelWorkbook annotation = clazz.getAnnotation(ExcelWorkbook.class);
        String excelName = annotation.value();
        ExcelSheet[] excelSheets = annotation.sheets();
        String[] sheetsName = new String[excelSheets.length];
        final int[] index = {0};
        Arrays.asList(excelSheets).stream().forEach(sheet -> sheetsName[index[0]++] = sheet.value());
        String excelType = annotation.type().toString().toLowerCase();

        Workbook workbook = new XSSFWorkbook();
        workbook.write(new FileOutputStream(Paths.get(dir, excelName + "." + excelType).toFile()));
        System.out.println("Write => " + Arrays.toString(contents));
    }

   /* public <T> void write(Class<T> clazz, T[]... contents) {
        com.wrox.utils.excel.annotation.ExcelWorkbook annotation = clazz.getAnnotation(com.wrox.utils.excel.annotation.ExcelWorkbook.class);
        String excelName = annotation.value();
        com.wrox.utils.excel.annotation.ExcelSheet[] sheets = annotation.sheets();
        String[] sheetsName = new String[sheets.length];
        final int[] index = {0};
        Arrays.asList(sheets).stream().forEach(sheet -> sheetsName[index[0]++] = sheet.value());
        String excelType = annotation.type().toString().toLowerCase();


    }*/
}
