package com.wrox.utils.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.Cell.*;

/**
 * Created by Dengbin on 2015/10/4.
 */
public class DefaultExcelReader implements ExcelReader {
    private static final Logger log = LogManager.getLogger();

    @Override
    public String[] readHeader(InputStream inputStream) {
        return new String[0];
    }

    @Override
    public String[] readHeader(Workbook workbook) {
        return new String[0];
    }

    @Override
    public <T> T readContent(InputStream inputStream, Class<T> clazz) throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return readContent(WorkbookFactory.create(inputStream), clazz);
    }

    @Override
    public <T> T readContent(Workbook workbook, Class<T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Map<String, Map<Integer, Map<String, Object>>> map = readContent(workbook);
        System.out.println(map);
        Map<Integer, Map<String, Object>> all = mergeContent(map);
        System.out.println(all);



        /*T obj = null;
        Method[] methods = clazz.getDeclaredMethods();

        for (Map<String, Object> contentMap : all.values()) {
            for (Map.Entry<String, Object> content : contentMap.entrySet()) {
                obj = clazz.newInstance();
                for (Method method : methods) {
                    if (method.getName().startsWith("set")) {
                        System.out.println(content.getValue() + "<----");
                        method.invoke(obj, content.getValue());
                    }
                }
            }
        }*/

//        Class<Clerk> cla = Clerk.class;
//        Object obj = cla.newInstance();
        Map<String, Object> m = all.get(1);
        System.out.println(m.get("行员代号") + " -- " + m.get("行员名称") + " -- " + m.get("日期"));
        T obj = clazz.newInstance();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", m.get("行员代号"));
        valueMap.put("name", m.get("行员名称"));
        valueMap.put("birthday", m.get("日期"));

        Map<String, Object> n = all.get(2);
        Map<String, Object> valueMap2 = new HashMap<>();
        valueMap2.put("id", n.get("行员代号"));
        valueMap2.put("name", n.get("行员名称"));
        valueMap2.put("birthday", n.get("日期"));

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(valueMap);
        list.add(valueMap2);

        T[] objects = mapper(clazz, list);
        System.out.println(Arrays.toString(objects));

//        Field[] fields = clazz.getDeclaredFields();
//        Map<String, Field> fieldMap = new HashMap<>(fields.length);
//        for (Field field : fields) {
//            field.setAccessible(true);
//            fieldMap.put(field.getName(), field);
//        }
//
//        mapper(obj, fieldMap, valueMap);

        /*for (Map.Entry<String, Field> fieldEntry : fieldMap.entrySet()) {
            fieldEntry.getValue().set(obj, valueMap.get(fieldEntry.getKey()));
        }*/
        /*Method[] methods = Arrays.asList(clazz.getDeclaredMethods()).stream()
                .filter(method -> method.getName().startsWith("set")).toArray(Method[]::new);

        Map<String, Method> methodMap = new HashMap<>(methods.length);
        for (Method method : methods) {
            methodMap.put(method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4), method);
        }

        for (Map.Entry<String, Method> method : methodMap.entrySet()) {
            method.getValue().invoke(obj, valueMap.get(method.getKey()));
        }*/

//        System.out.println(obj);


        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] mapper(Class<T> clazz, Collection<Map<String, Object>> contents) throws IllegalAccessException, InstantiationException {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<>(fields.length);
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        List<T> list = new ArrayList<>();
        for (Map<String, Object> map : contents) {
            T obj = clazz.newInstance();
            mapper(obj, fieldMap, map);
            System.out.println("-->" + obj);
            list.add(obj);
        }
        // type safety
        return (T[]) list.toArray();
    }

    private <T> void mapper(T obj, Map<Integer, Map<String, Object>> contents) {

    }

    private <T> void mapper(T obj, Map<String, Field> fields, Map<String, Object> contents) throws IllegalAccessException {
        for (Map.Entry<String, Field> fieldEntry : fields.entrySet()) {
            fieldEntry.getValue().setAccessible(true);
            fieldEntry.getValue().set(obj, contents.get(fieldEntry.getKey()));
        }
    }

    /**
     * 返回方法名称，去除前3位字符。<br/>
     * 例如：
     * getXxx/setXxx -> xxx
     *
     * @param methodName 需要处理的方法名
     * @return 处理过后的方法名
     */
    private String getMethodName(String methodName) {
        return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
    }


    /**
     * 当Excel工作簿中的表格都为同一类型的数据时将其合并方便操作。
     *
     * @param contents Excel工作簿的所有数据集合
     * @return Excel工作簿的数据集合，但不区分表格名称。
     */
    private Map<Integer, Map<String, Object>> mergeContent(Map<String, Map<Integer, Map<String, Object>>> contents) {
        Map<Integer, Map<String, Object>> all = new LinkedHashMap<>();
        int index = 1;
        for (Map<Integer, Map<String, Object>> map : contents.values()) {
            for (Map<String, Object> content : map.values()) {
                all.put(index++, content);
            }
        }

        return all;
    }

    /**
     * 返回整个工作簿的所有数据集合。
     *
     * @param workbook Excel工作簿
     * @return Excel工作簿的数据集合，并区分表格名称。
     */
    private Map<String, Map<Integer, Map<String, Object>>> readContent(Workbook workbook) {
        int totalContents = 0;
        int sheetsNum = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetsNum; i++) {
            totalContents += workbook.getSheetAt(i).getLastRowNum();
        }
        Map<String, Map<Integer, Map<String, Object>>> allContents = new LinkedHashMap<>(totalContents);

        for (int i = 0; i < sheetsNum; i++) {
            allContents.put(workbook.getSheetAt(i).getSheetName(), readContent(workbook.getSheetAt(i)));
        }

        return allContents;
    }

    /**
     * 返回Excel中某一张表格的所有数据集合。
     *
     * @param sheet Excel表格
     * @return Excel表格的数据集合
     */
    private Map<Integer, Map<String, Object>> readContent(Sheet sheet) {
        Map<Integer, Map<String, Object>> contents = new LinkedHashMap<>(sheet.getLastRowNum());
        Row headerRow = sheet.getRow(0);    // 表头
        // Excel表的总列数
        int colNum = headerRow.getPhysicalNumberOfCells();

        // 获取总行数
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row contentRow = sheet.getRow(i);
            Map<String, Object> rowContents = new LinkedHashMap<>(1);
            for (int col = 0; col < colNum; col++) {
                rowContents.put(headerRow.getCell(col).toString(), getCellValue(contentRow.getCell(col)));
            }
            contents.put(i, rowContents);
        }

        return contents;
    }

    /**
     * 返回Excel单元格的数据。
     *
     * @param cell Excel单元格
     * @return 单元格的数据
     */
    private Object getCellValue(Cell cell) {
        return getCellValue(cell, "yyyy-MM-dd");
    }

    /**
     * 返回Excel单元格的数据。
     *
     * @param cell Excel单元格
     * @return 单元格的数据
     */
    private Object getCellValue(Cell cell, String format) {
        return getCellValue(cell, new SimpleDateFormat(format));
    }

    /**
     * 返回Excel单元格的数据。
     *
     * @param cell Excel单元格
     * @return 单元格的数据
     */
    private <T extends DateFormat> String getCellValue(Cell cell, T formatter) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case CELL_TYPE_BLANK:
                return "";
            case CELL_TYPE_BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {   // 当公式值为Date时
                    return formatter.format(cell.getDateCellValue());
                }
                return Double.toString(cell.getNumericCellValue());
            case CELL_TYPE_STRING:
                return cell.getStringCellValue().trim();
            case CELL_TYPE_ERROR:
                log.warn("数据读取有误[{}]！", cell.toString());
                return null;
            case CELL_TYPE_FORMULA:
                try {
                    if (DateUtil.isCellDateFormatted(cell)) {   // 当公式值为Date时
                        return formatter.format(cell.getDateCellValue());
                    }
                    return Double.toString(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (Exception ex) {
                        return null;
                    }
                }
            default: return null;   // not happened
        }
    }

    public static void main(String[] args) throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ExcelReader reader = new DefaultExcelReader();
        InputStream inputStream = new FileInputStream("C:\\WorkSpace\\test2.xlsx");
        reader.readContent(inputStream, Clerk.class);
    }
}
