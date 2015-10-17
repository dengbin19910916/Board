package com.wrox.utils.excel;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.*;

/**
 * 读取Excel文件中的内容。
 * <p>
 * Created by Dengbin on 2015/10/12.
 */
public class ExcelReader {

    private Workbook workbook;

    private FormulaEvaluator evaluator;

    private String[] headers;

    public ExcelReader(Workbook workbook) {
        this.workbook = workbook;
        this.evaluator = this.workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.evaluateAll();
    }

    /**
     * 返回Excel工作簿中的所有表格的名称数组。
     *
     * @return Excel工作簿中的所有表格的名称数组
     */
    public String[] readSheetNames() {
        int count = workbook.getNumberOfSheets();
        String[] names = new String[count];

        for (int i = 0; i < names.length; i++) {
            names[i] = workbook.getSheetAt(i).getSheetName();
        }

        return names;
    }

    /**
     * 返回Excel表单的表头数组。默认取第一行数据为表头数据。
     *
     * @param index Excel表单的序号，从0开始计数。
     * @return Excel表单的表头数组
     */
    public String[] readSheetHeaders(int index) {
        return readSheetHeaders(index, 0);
    }

    /**
     * 返回Excel表单的表头数组。默认取第一行数据为表头数据。
     *
     * @param index  Excel表单的序号，从0开始计数。
     * @param rownum 表头所在行数，从0开始计数。
     * @return Excel表单的表头数组
     */
    public String[] readSheetHeaders(int index, int rownum) {
        return readSheetHeaders(workbook.getSheetAt(index), rownum);
    }

    /**
     * 返回Excel表单的表头数组。默认取第一行数据为表头数据。
     *
     * @param name Excel表单的名称
     * @return Excel表单的表头数组
     */
//    public String[] readSheetHeaders(String name) {
//        return readSheetHeaders(name, 0);
//    }

    /**
     * 返回Excel表单的表头数组。默认取第一行数据为表头数据。
     *
     * @param name   Excel表单的名称
     * @param rownum 表头所在行数，从0开始计数。
     * @return Excel表单的表头数组
     */
//    public String[] readSheetHeaders(String name, int rownum) {
//        return readSheetHeaders(workbook.getSheet(name), rownum);
//    }

    /**
     * 返回Excel表单的表头数组。
     *
     * @param sheet  Excel表单
     * @param rownum 表头所在行数，从0开始计数。
     * @return Excel表单的表头数组
     */
    private String[] readSheetHeaders(Sheet sheet, int rownum) {
        Row row = sheet.getRow(rownum);
        int colnum = row.getPhysicalNumberOfCells();
        String[] headers = new String[colnum];
        for (int i = 0; i < colnum; i++) {
            headers[i] = row.getCell(i).getStringCellValue();
        }

        return headers;
    }

    /**
     * 返回表单中的所有内容。默认表头为第一行。<br/>
     * 内容格式为：<br/>
     * 表头名:内容
     *
     * @param name Excel工作簿中的表单的名称
     * @return 表单中所有内容，不包含表头。
     */
    /*public Map<String, Object>[] readSheetContent(String name) {
        *//*
        在读取Excel文件内容时就需要Java类型的介入。
         *//*
        Sheet sheet = this.workbook.getSheet(name);
        int last = sheet.getLastRowNum();

        *//*for (int i = 1; i <= last; i++) {
            Row row = sheet.getRow(i);
            if (!isValid(row)) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println(String.valueOf(row.getCell(row.getFirstCellNum()).getStringCellValue()));
            }
        }*//*

        Row row = sheet.getRow(2);
        System.out.println(row.getCell(0).getErrorCellValue());

        return null;
    }*/

    /**
     * 返回表单中的所有内容。默认表头为第一行。<br/>
     * 内容格式为：<br/>
     * 表头名:内容
     *
     * @param name Excel工作簿中的表单的名称
     * @param clazz Excel解析对应的目标类型
     * @return 表单中所有内容，不包含表头。
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object>[] readSheetContent(String name, Class clazz) {
        return readSheetContent(name, clazz, null);
    }

    /**
     * 返回表单中的所有内容。默认表头为第一行。<br/>
     * 内容格式为：<br/>
     * 表头名:内容
     *
     * @param name Excel工作簿中的表单的名称
     * @param clazz Excel解析对应的目标类型
     * @param errors 返回错误信息
     * @return 表单中所有内容，不包含表头。
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object>[] readSheetContent(String name, Class clazz, Map<Integer, String> errors) {
        /*
        在读取Excel文件内容时就需要Java类型的介入。
         */
        List<Map<String, Object>> contents = new ArrayList<>();
        if (Objects.isNull(headers)) {
            headers = this.readSheetHeaders(0);
        }
        Sheet sheet = this.workbook.getSheet(name);
        ExcelAnnotationParser parser = new ExcelAnnotationParser();
        Map<String, Class> typeDirectory = parser.getTypeDirectory(clazz);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (isValid(row)) {
                Map<String, Object> content = new HashMap<>(headers.length);
                for (int j = 0; j < headers.length; j++) {
                    Object val = getCellValue(row.getCell(j), typeDirectory.get(headers[j]), null);
                    if (Objects.nonNull(val) && !(val instanceof FormulaError)) {
                        content.put(headers[j], val);
                    } else {
                        FormulaError error = (FormulaError) val;
                        assert error != null;
                        errors.put(error.getLongCode(), error.getString());
                    }
                }
                contents.add(content);
            }
        }

        // safe type
        return contents.stream().toArray(Map[]::new);
    }

    /**
     * 将Excel数据匹配到Java对象中。
     *
     * @param clazz Java对象类型
     * @param contents 需要进行匹配的数据
     * @param dictionary 进行匹配时的字段对照字典
     * @param <T> Java对象类型，用来强制转换数组类型
     * @return Java对象数组
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public <T> T[] mapper(Class<T> clazz, Map<String, Object>[] contents, Map<String, String> dictionary) throws IllegalAccessException, InstantiationException {
        Map<String, Field> fields = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            fields.put(field.getName(), field);
        }
        List<T> list = new ArrayList<>();
        for (Map<String, Object> content : contents) {
            T obj = clazz.newInstance();
            mapper(obj, fields, content, dictionary);
            list.add(obj);
        }
        // type safe
        T[] ts = (T[]) Array.newInstance(clazz, list.size());
        for (int i = 0; i < ts.length; i++) {
            ts[i] = list.get(i);
        }
        return ts;
    }

    /**
     * 将Excel数据匹配到Java对象中。
     *
     * @param obj 需要被赋值的对象
     * @param fields 需要被赋值的字段
     * @param content 为字段提供值的MAP集合
     * @param dictionary 字段名与Excel列名的对照表
     * @param <T> 需要被赋值的对象的类型
     * @throws IllegalAccessException
     */
    private <T> void mapper(T obj, Map<String, Field> fields, Map<String, Object> content, Map<String, String> dictionary) throws IllegalAccessException {
        for (Map.Entry<String, Field> fieldEntry : fields.entrySet()) {
            fieldEntry.getValue().setAccessible(true);
            fieldEntry.getValue().set(obj, content.get(dictionary.get(fieldEntry.getKey())));
        }
    }

    /**
     * 返回Excel工作簿单元格应该使用的数据类型。
     *
     * @param objType Javabean字段的数据类型
     * @return Excel工作簿单元格应该使用的数据类型
     */
    /*private int getCellType(Class objType) {
        *//*if (objType == Byte.class || objType == Short.class || objType == Integer.class || objType == Long.class
                || objType == Float.class || objType == Double.class) {
            return Cell.CELL_TYPE_NUMERIC;
        }*//*
        if (objType.getSuperclass() == Number.class) {
            return Cell.CELL_TYPE_NUMERIC;
        }
        if (objType == String.class) {
            return Cell.CELL_TYPE_STRING;
        }


        return 0;
    }*/

    /**
     * 返回单元格的内容。
     *
     * @param cell 单元格
     * @param format 单元格式化
     * @return 单元格的内容
     */
    private <T> Object getCellValue(Cell cell, Class<T> objType, Format format) {
        /*
        此处需要先对单元格设置数据格式
         */
        if (Objects.isNull(cell)) {
            return null;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {   // 单元格数据为空时直接返回null，表示此单元格数据无效。
            return null;
        } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {   // 单元格数据错误，返回错误信息。
            return FormulaError.forInt(cell.getErrorCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {  // 单元格为公式，将公式计算后在进行取值。
            CellValue value = evaluator.evaluate(cell);
            switch (value.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    return value.getStringValue();
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        if (objType == Date.class || objType.getSuperclass() == Date.class) {
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            return cell.getDateCellValue();
                        }
                        if (objType == Calendar.class || objType.getSuperclass() == Calendar.class) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(cell.getDateCellValue());
                            return calendar;
                        }
                        if (Arrays.asList(objType.getInterfaces()).contains(Temporal.class)) {
                            return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue()));
                        }

                        return DateUtil.getJavaDate(value.getNumberValue());
                    }
                    return value.getNumberValue();
                case Cell.CELL_TYPE_BOOLEAN:
                    return value.getBooleanValue();
                case Cell.CELL_TYPE_ERROR:
                    return FormulaError.forInt(value.getErrorValue());
                default:
                    return null;
            }
        } else {    // 单元格数据为String，Numeric和Boolean类型时，返回表格对应的数据。
            if (objType == String.class) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
            }
            if (objType.getSuperclass() == Number.class) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    Number number = NumberUtils.createNumber(Double.toString(cell.getNumericCellValue()));
                    if (objType == BigInteger.class) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        return new BigInteger(cell.getStringCellValue().split("\\.")[0]);
                    } else if (objType == BigDecimal.class) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        return new BigDecimal(cell.getStringCellValue());
                    } else {
                        return getPrimitiveValue(number, objType);
                    }
                }
            }
            if (objType == Boolean.class) {
                cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
                return cell.getBooleanCellValue();
            }
            if (objType == Date.class || objType.getSuperclass() == Date.class) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                return cell.getDateCellValue();
            }
            if (objType == Calendar.class || objType.getSuperclass() == Calendar.class) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(cell.getDateCellValue());
                return calendar;
            }
            if (Arrays.asList(objType.getInterfaces()).contains(Temporal.class)) {
                return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue()));
            }

            return null;
        }
    }

    /**
     * 获取基本类型数据。
     *
     * @param number 数据对象
     * @param type 数据包装类型
     * @param <T> 目标数据
     * @return 目标基本类型数据
     */
    @SuppressWarnings("unchecked")
    private <T> T getPrimitiveValue(Number number, Class<T> type) {
        if (Objects.isNull(number)) {
            return null;
        }

        if (type == Byte.class) {
            return (T) NumberUtils.createNumber(String.valueOf(number.byteValue()));
        }
        if (type == Short.class) {
            return (T) NumberUtils.createNumber(String.valueOf(number.shortValue()));
        }
        if (type == Integer.class || Objects.isNull(type)) {
            return (T) NumberUtils.createNumber(String.valueOf(number.intValue()));
        }
        if (type == Long.class) {
            return (T) NumberUtils.createNumber(String.valueOf(number.longValue()));
        }
        if (type == Float.class) {
            return (T) NumberUtils.createNumber(String.valueOf(number.floatValue()));
        }
        if (type == Double.class) {
            return (T) NumberUtils.createNumber(String.valueOf(number.doubleValue()));
        }

        return null;
    }

    /*private Class getFatherWrapType(Class clz) {
        if (clz == Byte.class || clz == Short.class || clz == Integer.class || clz == Long.class
                || clz == Float.class || clz == Double.class) {
            return Number.class;
        }
        return clz;
    }*/

    /*private Object getFormulaCellValue(Cell cell) {
        CellValue cellValue = evaluator.evaluate(cell);
        switch (cellValue.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cellValue.getStringValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cellValue.getNumberValue();
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_ERROR:
                return FormulaError.forInt(cell.getErrorCellValue());
            default:
                return null;
        }
    }*/

    /**
     * 验证Excel文件的数据行是否有效，数据默认从第一列开始。
     *
     * @param row Excel文件的数据行
     * @return 数据行的有效性。true - 有效， false - 无效。
     */
    private boolean isValid(Row row) {
        return isValid(row, (short) 0);
    }

    /**
     * 验证Excel文件的数据行是否有效。
     *
     * @param row Excel文件的数据行
     * @param dataIndex 数据的开始列的索引
     * @return 数据行的有效性。true - 有效， false - 无效。
     */
    private boolean isValid(Row row, short dataIndex) {
        try {
            return row.getFirstCellNum() == dataIndex;
        } catch (NullPointerException e) {
            return false;
        }
    }


//    public Map<String, Object>[] readSheetContent(int index) {
//        return null;
//    }
}
