package com.wrox.utils.excel;

import com.google.gson.*;
import com.wrox.utils.excel.model.ExcelError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 读取Excel文件中的内容。
 * <p>
 * Created by Dengbin on 2015/10/12.
 */
public class ExcelReader {
    private static final Logger log = LogManager.getLogger();

    /**
     * Excel工作簿。
     */
    private Workbook workbook;
    /**
     * Excel工作簿中的所有表单，用于缓存表单信息。
     */
    private Sheet[] sheets;
    /**
     * Excel工作簿的公式执行器。
     */
    private FormulaEvaluator evaluator;
    /**
     * Excel工作簿的表头信息。
     */
    private String[] headers;
    /**
     * 保存Excel工作簿中的单元格的错误信息。
     */
    private ExcelError errors;

    public ExcelReader(@NotNull Workbook workbook) {
        assert workbook != null;
        this.workbook = workbook;
        this.evaluator = this.workbook.getCreationHelper().createFormulaEvaluator();
        this.errors = new ExcelError();
    }

    /**
     * 判断Excel工作簿的单元格是否有错误信息。
     *
     * @return true - 有错误信息，false - 无错误信息。
     */
    public boolean hasErrors() {
        return this.errors.size() > 0;
    }

    /**
     * 返回Excel工作簿的单元格的错误信息。
     *
     * @return Excel工作簿的单元格的错误信息。
     */
    public ExcelError getErrors() {
        return this.errors;
    }

    /**
     * 返回Excel工作簿中的所有表单。
     *
     * @return Excel工作簿中的所有表单对象数组。
     */
    public Sheet[] getSheets() {
        if (Objects.nonNull(this.sheets)) {
            return this.sheets;
        }
        int num = this.workbook.getNumberOfSheets();
        this.sheets = new Sheet[num];
        for (int i = 0; i < sheets.length; i++) {
            sheets[i] = this.workbook.getSheetAt(i);
        }
        return sheets;
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
     * 返回Excel表单的表头数组。默认取第一行数据为表头数据，表单为第一个表单。
     *
     * @return Excel表单的表头数组
     */
    public String[] readSheetHeaders() {
        return readSheetHeaders(0, 0);
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
     * [{表头名:内容}]<br/>
     * 此方法返回的是最基本的Excel工作簿的表单的数据，未进行过任何方式的类型处理。
     *
     * @return 表单中所有内容，不包含表头。
     * @see ExcelError Excel工作簿的单元格的错误信息。
     */
    @SuppressWarnings("unchecked")
    public <T> T[] readSheetContent(Class<T> clazz) {
        List<T[]> list = new ArrayList<>();
        for (int i = 0; i < this.workbook.getNumberOfSheets(); i++) {
            // 获取所有表单的数据
            list.add(this.readSheetContent(clazz, i));
        }

        final int[] length = {0};
        list.stream().forEach(ts -> length[0] += ts.length);    // 获取目标类型的结果数组的长度
        // type safe
        T[] elements = (T[]) Array.newInstance(clazz, length[0]);  // 通过反射创建目标类型的结果数组
        final int[] index = {0};
        list.stream().forEach(ts -> Arrays.asList(ts).forEach(t -> elements[index[0]++] = t));  // 对结果数组进行赋值

        return elements;
    }

    /**
     * 返回由表单中的所有数据行转换成的对象数组。
     *
     * @param clazz Excel工作簿转换的目标类型
     * @param indexs Excel工作簿中的表单所在位置，索引从0开始。
     * @return 表单中所有内容，不包含表头。
     * @see ExcelError Excel工作簿的单元格的错误信息。
     */
    public <T> T[] readSheetContent(Class<T> clazz, int... indexs) {
        List<Map<String, String>[]> list = new ArrayList<>(indexs.length);
        for (int index : indexs) {
            list.add(readSheetContent(workbook.getSheetAt(index)));
        }
        return match(clazz, merge(list), ExcelAnnotationUtils.getNameDirectory(clazz));
    }

    public <T> T[] readSheetContent(Class<T> clazz, String... names) {
        List<Map<String, String>[]> list = new ArrayList<>(names.length);
        for (String name : names) {
            list.add(readSheetContent(workbook.getSheet(name)));
        }
        return match(clazz, merge(list), ExcelAnnotationUtils.getNameDirectory(clazz));
    }

    /**
     * 返回表单中的所有内容。默认表头为第一行。<br/>
     * 内容格式为：<br/>
     * 表头名:内容
     *
     * @param sheet Excel工作簿中的表单对象
     * @return 表单中所有内容，不包含表头。
     * @see ExcelError Excel工作簿的单元格的错误信息。
     */
    @SuppressWarnings("unchecked")
    private Map<String, String>[] readSheetContent(Sheet sheet) {
        List<Map<String, String>> contents = new ArrayList<>();
        if (Objects.isNull(headers)) {
            headers = this.readSheetHeaders(0); // 默认表头为第一行
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (isValid(row)) {
                Map<String, String> content = new HashMap<>(headers.length);
                for (int j = 0; j < headers.length; j++) {
                    // 将单元格的数据放入集合中，单元格有错误时返回null
                    content.put(headers[j], getCellValue(row.getCell(j), this.errors));
                }
                contents.add(content);
            }
        }
        // safe type
        return contents.stream().toArray(Map[]::new);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String>[] merge(List<Map<String, String>[]> contents) {
        if (Objects.isNull(contents)) {
            return null;
        }
        final int[] length = {0};
        contents.stream().forEach(content -> length[0] += content.length);

        final int[] index = {0};
        Map<String, String>[] data = new Map[length[0]];
        contents.stream().forEach(content -> Arrays.asList(content).stream().forEach(c -> data[index[0]++] = c));

        return data;
    }

    /**
     * 将Excel数据匹配到Java对象中。
     *
     * @param clazz      Java对象类型
     * @param contents   需要进行匹配的数据
     * @param dictionary 进行匹配时的字段对照字典
     * @param <T>        Java对象类型，用来强制转换数组类型
     * @return Java对象数组
     */
    @SuppressWarnings({"unchecked", "serial"})
    public <T> T[] match(Class<T> clazz, Map<String, String>[] contents, Map<String, String> dictionary) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (json, typeOfT, context) ->
                                new JsonPrimitive(json.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                                LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .registerTypeAdapter(LocalTime.class,
                        (JsonSerializer<LocalTime>) (json, typeOfT, context) ->
                                new JsonPrimitive(json.format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .registerTypeAdapter(LocalTime.class,
                        (JsonDeserializer<LocalTime>) (json, typeOfT, context) ->
                                LocalTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("HH:mm:ss")))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (json, typeOfT, context) ->
                                new JsonPrimitive(json.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .create();

        List<Map<String, String>> list = new ArrayList<>();
//        Arrays.asList(contents).stream().forEach(content -> content.entrySet().stream().forEach(
//                entry -> list.add(new HashMap<String, String>() {{put(dictionary.get(entry.getKey()), entry.getValue());}}))
//        );
//        Arrays.asList(contents).stream().forEach();
        for (Map<String, String> content : contents) {
            Map<String, String> map = new HashMap<>(content.size());
            for (Map.Entry<String, String> entry : content.entrySet()) {
                map.put(dictionary.get(entry.getKey()), entry.getValue());
            }
            list.add(map);
        }

        // type safe
        return gson.fromJson(gson.toJson(list), (Type) Array.newInstance(clazz, 0).getClass());
    }

    /**
     * 返回单元格的内容。
     *
     * @param cell 单元格
     * @return 单元格的内容
     */
    private String getCellValue(@NotNull Cell cell, @NotNull ExcelError error) {
        DataFormatter formatter = new DataFormatter();
        ExcelStyleDateFormatter dateFormatter = new ExcelStyleDateFormatter("yyyy-MM-dd");

        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_FORMULA) {   // 单元格为公式，将公式计算后在进行取值。
            if (Objects.isNull(evaluator)) {
                return formatter.formatCellValue(cell);
            }
            cellType = evaluator.evaluateFormulaCell(cell);
        }
        switch (cellType) {
            case Cell.CELL_TYPE_BLANK:      // 单元格数据为空时直接返回null，表示此单元格数据无效。
                error.put(new CellReference(cell), "单元格不能为空。");
                log.warn("单元格{}不能为空。", new CellReference(cell).formatAsString());
                return null;
            case Cell.CELL_TYPE_ERROR:      // 单元格数据错误，返回错误信息。
                error.put(new CellReference(cell), "单元格数据错误[" + FormulaError.forInt(cell.getErrorCellValue()).getString() + "]。");
                log.warn("单元格数据错误[{}]。", FormulaError.forInt(cell.getErrorCellValue()).getString());
                return null;
            case Cell.CELL_TYPE_BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());

            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return dateFormatter.format(cell.getDateCellValue());
                }
                return formatter.formatCellValue(cell);
            default:
                error.put(new CellReference(cell), "未知的单元格数值[" + formatter.formatCellValue(cell) + "]！");
                log.warn("未知的单元格数值[{}]！", formatter.formatCellValue(cell));
                return null;
        }
    }

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
     * @param row       Excel文件的数据行
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
}
