package com.wrox.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.poi.ss.usermodel.Cell.*;

/**
 * Excel文件读取。
 *
 * Created by Dengbin on 2015/9/29.
 */
public class ExcelReaderUtil {
    private static final Logger log = LogManager.getLogger();

    private Workbook workbook;
    private Sheet sheet;
    private Row row;

    /**
     * 读取Excel表格表头的内容。
     *
     * @param filepath 文件路径
     * @return 表头内容的数组
     * @throws FileNotFoundException 文件不存在
     */
    public String[] readExcelTitle(String filepath) throws FileNotFoundException {
        return readExcelTitle(new FileInputStream(filepath), ExcelUtils.getExcelVersion(filepath));
    }

    /**
     * 读取Excel表格表头的内容。
     *
     * @param inputStream Excel文件输入流
     * @param excelVersion Excel文件的版本
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream inputStream, ExcelUtils.ExcelVersion excelVersion) {
        try {
            workbook = excelVersion == ExcelUtils.ExcelVersion.LOW ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            log.warn("输入流不可读。");
        }
        sheet = workbook.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getCellFormatValue(row.getCell(i));
        }
        return title;
    }

    /**
     * 读取Excel表格表头的内容。
     *
     * @param filepath 文件路径
     * @return 表头内容的数组
     * @throws FileNotFoundException 文件不存在
     */
    public Map<Integer, String> readExcelContent(String filepath) throws FileNotFoundException {
        return readExcelContent(new FileInputStream(filepath), ExcelUtils.getExcelVersion(filepath));
    }

    /**
     * 读取Excel数据内容。
     *
     * @param inputStream 输入流
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readExcelContent(InputStream inputStream, ExcelUtils.ExcelVersion excelVersion) {
        Map<Integer, String> content = new HashMap<>();
        String str = "";
        try {
            workbook = excelVersion == ExcelUtils.ExcelVersion.LOW ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        System.out.println("行数=>" + rowNum);
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() + "-";
                str += getCellFormatValue(row.getCell(j)).trim() + "\t";
                j++;
            }
            content.put(i, str);
            str = "";
        }
        return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(Cell cell) {
        String strCell;
        switch (cell.getCellType()) {
            case CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("")) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(Cell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                result = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.DATE);
//                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            } else if (cellType == CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据Cell类型设置数据。
     *
     * @param cell Excel单元格
     * @return 单元格的数据
     */
    private String getCellFormatValue(Cell cell) {
        String cellValue;
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case CELL_TYPE_BOOLEAN:
                    cellValue = Boolean.valueOf(cell.getBooleanCellValue()).toString();
                    break;
                case CELL_TYPE_NUMERIC:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case CELL_TYPE_FORMULA:
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = dateFormat.format(date);

                    } else {
                        // 如果是纯数字，取得当前Cell的数值
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                default: cellValue = "";    // 默认的Cell值
            }
        } else {
            cellValue = "";
        }
        return cellValue;

    }

    public static void main(String[] args) {
        String filepath = "C:\\WorkSpace\\test2.xlsx";
        try {
            // 对读取Excel表格标题测试
//            InputStream is = new FileInputStream(filepath);
            ExcelReaderUtil excelReader = new ExcelReaderUtil();
//            String[] title = excelReader.readExcelTitle(is);
            String[] title = excelReader.readExcelTitle(filepath);
            System.out.println("获得Excel表格的标题:");
            for (String s : title) {
                System.out.print(s + "\t");
            }
            System.out.println();

            // 对读取Excel表格内容测试
//            InputStream is2 = new FileInputStream("C:\\WorkSpace\\test2.xlsx");
            Map<Integer, String> map = excelReader.readExcelContent(filepath);
            System.out.println("获得Excel表格的内容:");
            for (int i = 1; i <= map.size(); i++) {
                System.out.println(map.get(i));
            }

        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
    }
}
