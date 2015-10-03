package com.wrox.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;

/**
 * Excel文件的工具类。
 *
 * Created by Dengbin on 2015/9/30.
 */
public final class ExcelUtils {
    private static final Logger log = LogManager.getLogger();

    /**
     * 此工具类没有对象实例
     */
    protected ExcelUtils() {
        super();
    }

    /**
     * 将Java集合中的数据以Excel的形式输出到制定的IO设备中。
     *
     * @param title 表格标题
     * @param headers 表格属性列名数组
     * @param datas 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。
     *              此方法支持的javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param output 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public static void writeExcel(String title, String[] headers, Collection<T> datas,
                                  OutputStream output, String pattern) {
        writeExcel(title, headers, datas, output, pattern, ExcelVersion.LOW);

    }

    /**
     * 将Java集合中的数据以Excel的形式输出到制定的IO设备中。
     *
     * @param title 表格标题，默认为sheet1
     * @param headers 表格属性列名数组
     * @param datas 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。
     *              此方法支持的javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param output 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     * @param excelVersion Excel的版本，默认为2003版
     */
    public static void writeExcel(String title, String[] headers, Collection<T> datas,
                                  OutputStream output, String pattern, ExcelVersion excelVersion) {
        // 声明一个Excel工作簿
        Workbook workbook = excelVersion == ExcelVersion.HIGH ? new XSSFWorkbook() : new HSSFWorkbook();
        // 生成一个表格
        Sheet sheet = workbook.createSheet(title == null ? "sheet1" : title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 生成表头样式
        CellStyle headerStyle = workbook.createCellStyle();
        // 设置单元格样式
        headerStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);

        // 生成一个字体
        Font headerFont = workbook.createFont();
        headerFont.setColor(HSSFColor.VIOLET.index);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        headerStyle.setFont(headerFont);

        // 生成内容样式
        CellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        contentStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font contentFont = workbook.createFont();
        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contentStyle.setFont(contentFont);

        // 声明一个画图的顶级管理器
        Drawing drawing = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置
        Comment comment = drawing.createCellComment(excelVersion == ExcelVersion.HIGH ?
                        new XSSFClientAnchor(0, 0, 0, 0, 4, 2, 6, 5) : new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        comment.setString(excelVersion == ExcelVersion.HIGH ?
                new XSSFRichTextString("可以在POI中添加注释！") : new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容。
        comment.setAuthor("邓斌");

        // 产生表格标题行
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            RichTextString text = excelVersion == ExcelVersion.HIGH ? new XSSFRichTextString(headers[i]) : new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 便利集合数据，产生数据行
        Iterator<T> iterator = datas.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            row = sheet.createRow(index++);
            T t = iterator.next();
            // 利用反射，更具javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getFields();
            for (int i = 0; i < fields.length; i++) {
                Cell cell = row.getCell(i);
                cell.setCellStyle(headerStyle);
                String methodName = "get" + fields[i].getName().substring(0, 1).toUpperCase() + fields[i].getName().substring(1);

                Class tClass = t.getClass();
                try {
                    Method method = tClass.getMethod(methodName);
                    Object objectValue = method.invoke(t);
                    String value = null;

                    if (objectValue instanceof Boolean) {

                    }
                } catch (NoSuchMethodException e) {
                    log.warn("方法{}不存在！", methodName);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    log.warn("方法{}调用出错！", methodName);
                }
            }
        }
    }

    /**
     * 返回Excel文件版本。
     *
     * @param filepath Excel文件路径
     * @return Excel文件的版本
     * @throws FileNotFoundException
     */
    public static ExcelVersion getExcelVersion(String filepath) throws FileNotFoundException {
        return getExcelVersion(Paths.get(filepath));
    }

    /**
     * 返回Excel文件版本。
     *
     * @param filepath Excel文件路径对象
     * @return Excel文件的版本
     * @throws FileNotFoundException
     */
    public static ExcelVersion getExcelVersion(Path filepath) throws FileNotFoundException {
        return getExcelVersion(filepath.toFile());
    }

    /**
     * 返回Excel文件版本。
     *
     * @param excelFile Excel文件对象
     * @return Excel文件的版本
     * @throws FileNotFoundException
     */
    public static ExcelVersion getExcelVersion(File excelFile) throws FileNotFoundException {
        return getExcelVersion(new FileInputStream(excelFile));
    }

    /**
     * 返回Excel文件的版本。
     *
     * @param inputStream Excel文件输入流
     * @return Excel文件版本
     */
    public static ExcelVersion getExcelVersion(InputStream inputStream) {
        try {
            new HSSFWorkbook(inputStream);
        } catch (OfficeXmlFileException | IOException e) {
            return ExcelUtils.ExcelVersion.HIGH;
        }
        return ExcelUtils.ExcelVersion.LOW;
    }

    /**
     * Excel版本
     */
    public enum ExcelVersion {
        /**
         * 高版本，2007+
         */
        HIGH,
        /**
         * 低版本，2003-
         */
        LOW
    }
}
