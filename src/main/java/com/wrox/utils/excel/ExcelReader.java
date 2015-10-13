package com.wrox.utils.excel;

import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Type;
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
    public String[] readSheetHeaders(String name) {
        return readSheetHeaders(name, 0);
    }

    /**
     * 返回Excel表单的表头数组。默认取第一行数据为表头数据。
     *
     * @param name   Excel表单的名称
     * @param rownum 表头所在行数，从0开始计数。
     * @return Excel表单的表头数组
     */
    public String[] readSheetHeaders(String name, int rownum) {
        return readSheetHeaders(workbook.getSheet(name), rownum);
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
     * 表头名:内容
     *
     * @param name Excel工作簿中的表单的名称
     * @return 表单中所有内容，不包含表头。
     */
    public Map<String, Object>[] readSheetContent(String name) {
        /*
        在读取Excel文件内容时就需要Java类型的介入。
         */
        Sheet sheet = this.workbook.getSheet(name);
        int last = sheet.getLastRowNum();

        /*for (int i = 1; i <= last; i++) {
            Row row = sheet.getRow(i);
            if (!isNullRow(row)) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println(String.valueOf(row.getCell(row.getFirstCellNum()).getStringCellValue()));
            }
        }*/

        Row row = sheet.getRow(2);
        System.out.println(row.getCell(0).getErrorCellValue());

        return null;
    }

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

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Map<String, Object> content = new HashMap<>(headers.length);
            for (int j = 0; j < headers.length; j++) {
                content.put(headers[j], getCellValue(row.getCell(j), null));
            }
            contents.add(content);
        }

        // safe type
        return contents.stream().toArray(Map[]::new);
    }

    /**
     * 返回单元格的内容。
     *
     * @param cell 单元格
     * @return 单元格的内容
     */
    private Object getCellValue(Cell cell, Type type) {
        if (Objects.isNull(cell)) {
            return null;
        }

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                System.out.println("公式进入");
                return getFormulaCellValue(cell);
            default:
                return null;
        }
    }

    private Object getFormulaCellValue(Cell cell) {
        CellValue cellValue = evaluator.evaluate(cell);
        switch (cellValue.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cellValue.getStringValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return null;
        }
    }

    private boolean isNullRow(Row row) {
        try {
            row.getFirstCellNum();
            return false;
        } catch (NullPointerException e) {
            return true;
        }
    }


    public Map<String, Object>[] readSheetContent(int index) {
        return null;
    }
}
