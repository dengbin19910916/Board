package com.wrox.utils.excel.operator;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Excel文件处理的工具类。
 *
 * @author dengb
 * @version 1.0
 */
public class Excels {

    /**
     * Excel文件数据与对象字段的匹配器。
     */
    private ExcelMapper mapper;

    public Excels() {
        this.mapper = new AnnotationExcelMapper();
    }

    /**
     * 读取Excel文件中所有的文件内容。
     *
     * @param clazz    Excel文件的映射类型对象。
     * @param filepath Excel工作簿的文件路径。
     * @param <T>      Excel文件映射的目标类型。
     * @return 目标对象数组。
     */
    public <T> T[] read(Class<T> clazz, String filepath) throws IOException, SAXException, OpenXML4JException {
        return read(clazz, new File(filepath));
    }

    /**
     * 读取Excel文件中所有的文件内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param file  Excel工作簿的文件对象。
     * @param <T>   Excel文件映射的目标类型。
     * @return 目标对象数组。
     */
    public <T> T[] read(Class<T> clazz, File file) throws IOException, SAXException, OpenXML4JException {
        return read(clazz, new FileInputStream(file));
    }

    /**
     * 读取Excel文件中所有的文件内容。
     *
     * @param clazz Excel文件的映射类型对象。
     * @param is    Excel工作簿的文件输入流对象。
     * @param sheetIndexs 需要解析的Excel表单索引。
     * @param <T>   Excel文件映射的目标类型。
     * @return 目标对象数组。
     */
    public <T> T[] read(Class<T> clazz, InputStream is, int... sheetIndexs) throws OpenXML4JException, SAXException, IOException {
        return mapper.match(clazz, new XSSFExcelReader(is).readSheets(sheetIndexs));
    }

    public void setMapper(ExcelMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Excels对象的构造器。
     */
    public static class Builder {
        private String dateFormat = "yyyy-MM-dd";
        private String timeFormat = "HH:mm:ss";
        private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

        /**
         * 设置LocalDate的解析格式。
         *
         * @param dateFormat LocalDate的解析格式。
         * @return Excels的构造器。
         */
        public Builder setLocalDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
            return this;
        }

        /**
         * 设置LocalTime的解析格式。
         *
         * @param timeFormat LocalTime的解析格式。
         * @return Excels的构造器。
         */
        public Builder setLocalTimeFormat(String timeFormat) {
            this.timeFormat = timeFormat;
            return this;
        }

        /**
         * 设置LocalDateTime的解析格式。
         *
         * @param dateTimeFormat LocalDateTime的解析格式。
         * @return Excels的构造器。
         */
        public Builder setLocalDateTimeFormat(String dateTimeFormat) {
            this.dateTimeFormat = dateTimeFormat;
            return this;
        }

        /**
         * 构造Excels对象。
         *
         * @return Excels对象
         * @throws IOException
         * @throws SAXException
         * @throws OpenXML4JException
         */
        public Excels create() throws IOException, SAXException, OpenXML4JException {
            Excels e = new Excels();
            e.setMapper(new AnnotationExcelMapper(dateFormat, timeFormat, dateTimeFormat));
            return e;
        }
    }
}
