package com.wrox.utils.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Excel文件工具类。这个类提供一些静态方法用于读取与创建Excel文件。
 *
 * Created by Dengbin on 2015/10/12.
 */
public final class Excels {

    public static Workbook create(File file, String password, boolean readOnly) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file, password, readOnly);
        if (workbook instanceof XSSFWorkbook) {
            workbook = new SXSSFWorkbook((XSSFWorkbook) workbook);
        }
        System.out.println(workbook.getNumberOfSheets());
        return workbook;
    }

    /**
     * 读取Excel文件中所有的文件内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param file Excel工作簿的文件对象。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static <T> T[] read(Class<T> clazz, File file) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader(new SXSSFWorkbook((XSSFWorkbook) WorkbookFactory.create(file, null, true)));
        return reader.readSheetContent(clazz);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param file Excel工作簿的文件对象。
     * @param indexs 需要读取的Excel工作簿中的表单序号，表单序号从0开始。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static <T> T[] read(Class<T> clazz, File file, int... indexs) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader(create(file, null, true));
        return reader.readSheetContent(clazz, indexs);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param file Excel工作簿的文件对象。
     * @param names 需要解析的Excel工作簿的表单名称数组。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static <T> T[] read(Class<T> clazz, File file, String... names) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader(create(file, null, true));
        return reader.readSheetContent(clazz, names);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param filepath Excel工作簿的路径对象。
     * @param clazz Excel文件的映射类型对象。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static <T> T[] read(Class<T> clazz, Path filepath) throws IOException, InvalidFormatException {
        return read(clazz, filepath.toFile());
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param filepath Excel工作簿的路径对象。
     * @param indexs 需要读取的Excel工作簿中的表单序号，表单序号从0开始。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static <T> T[] read(Class<T> clazz, Path filepath, int... indexs) throws IOException, InvalidFormatException {
        return read(clazz, filepath.toFile(), indexs);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param filepath Excel工作簿的文件路径对象。
     * @param names 需要解析的Excel工作簿的表单名称数组。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static <T> T[] read(Class<T> clazz, Path filepath, String... names) throws IOException, InvalidFormatException {
        return read(clazz, filepath.toFile(), names);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param inputStream Excel文件输入流。
     * @param clazz Excel文件的映射类型对象。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static <T> T[] read(Class<T> clazz, InputStream inputStream) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader(WorkbookFactory.create(inputStream));
        return reader.readSheetContent(clazz);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param inputStream Excel工作簿的文件输入流对象。
     * @param indexs 需要读取的Excel工作簿中的表单序号，表单序号从0开始。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static <T> T[] read(Class<T> clazz, InputStream inputStream, int... indexs) throws IOException, InvalidFormatException {
        return read(clazz, WorkbookFactory.create(inputStream), indexs);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param inputStream Excel工作簿的文件输入流对象。
     * @param names 需要解析的Excel工作簿的表单名称数组。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static <T> T[] read(Class<T> clazz, InputStream inputStream, String... names) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader(WorkbookFactory.create(inputStream));
        return reader.readSheetContent(clazz, names);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param workbook Excel工作簿对象。
     * @param clazz Excel文件的映射类型对象。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     */
    public static <T> T[] read(Class<T> clazz, Workbook workbook) {
        ExcelReader reader = new ExcelReader(workbook);
        return reader.readSheetContent(clazz);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param workbook Excel工作簿对象。
     * @param indexs 需要读取的Excel工作簿中的表单序号，表单序号从0开始。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     */
    public static <T> T[] read(Class<T> clazz, Workbook workbook, int... indexs) {
        ExcelReader reader = new ExcelReader(workbook);
        return reader.readSheetContent(clazz, indexs);
    }

    /**
     * 读取Excel文件中的内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param workbook Excel工作簿对象。
     * @param names 需要解析的Excel工作簿的表单名称数组。
     * @param <T> Excel文件映射的目标类型。
     * @return 目标对象数组。
     */
    public static <T> T[] read(Class<T> clazz, Workbook workbook, String... names) {
        ExcelReader reader = new ExcelReader(workbook);
        return reader.readSheetContent(clazz, names);
    }

    /**
     * 创建Excel文件，并向其中写入内容。
     */
    public static <T> void write(Class<T> clazz, T[] contents) throws IOException {
        ExcelWriter writer = new ExcelWriter();
        writer.write(clazz, contents);
    }
}
