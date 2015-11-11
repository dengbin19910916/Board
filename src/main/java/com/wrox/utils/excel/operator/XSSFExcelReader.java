package com.wrox.utils.excel.operator;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 使用事件处理方式读取2007+版本的Excel工作簿。
 * <p>
 * @author dengb
 * @version 1.0
 */
public class XSSFExcelReader implements ExcelReader {
    private static final Logger log = LogManager.getLogger();

    private List<Map<String, String>> workbookData;
    /**
     * Excel文档阅读器，用于读取Excel的底层XML文件。
     */
    private XSSFReader reader;
    /**
     * XML文档阅读器。
     */
    private XMLReader parser;

    /**
     * 通过Excel文件对象构造XSSFExcelReader对象。
     *
     * @param file Excel文件对象。
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     */
    public XSSFExcelReader(File file) throws IOException, OpenXML4JException, SAXException {
        this(new FileInputStream(file));
    }

    /**
     * 通过Excel文件的输入对象构造XSSFExcelReader对象。
     *
     * @param is Excel文件的输入对象。
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     */
    public XSSFExcelReader(InputStream is) throws IOException, OpenXML4JException, SAXException {
        this.workbookData = new ArrayList<>();

        OPCPackage pkg = OPCPackage.open(is);
        reader = new XSSFReader(pkg);
        SharedStringsTable sst = reader.getSharedStringsTable();
        StylesTable st = reader.getStylesTable();
        parser = fetchSheetParser(sst, st);
    }

    /**
     * 通过Excel文件的路径构造XSSFExcelReader对象。
     *
     * @param filepath Excel文件的路径。
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     */
    public XSSFExcelReader(String filepath) throws IOException, OpenXML4JException, SAXException {
        this(new FileInputStream(filepath));
    }

    /**
     * Sheet.xml文档处理，当未传入参数时处理所有表单。
     *
     * @param sheetIndex 需要处理的sheet.xml的索引。
     * @throws OpenXML4JException
     * @throws IOException
     * @throws SAXException
     */
    public void process(int... sheetIndex) throws OpenXML4JException, IOException, SAXException {
        if (sheetIndex.length == 0) {
            Iterator<InputStream> iterator = reader.getSheetsData();
            while(iterator.hasNext()) {
                try (InputStream is = iterator.next()) {
                    InputSource sheet = new InputSource(is);
                    parser.parse(sheet);
                }
            }
        } else {
            for (int index : sheetIndex) {
                try (InputStream is = reader.getSheet("rId" + index)) {
                    InputSource sheet = new InputSource(is);
                    parser.parse(sheet);
                }
            }
        }
    }

    /**
     * 查找一个可用的XML文档解析器。
     *
     * @return XML文档阅读器。
     * @throws SAXException
     */
    public XMLReader fetchSheetParser(SharedStringsTable sst, StylesTable st) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        ContentHandler handler = new SheetHandler(sst, st); // 需要被优化
        parser.setContentHandler(handler);
        return parser;
    }

    @Override
    public List<Map<String, String>> readSheets(int... sheetIndex) {
        try {
            process(sheetIndex);
        } catch (OpenXML4JException | IOException | SAXException e) {
            log.error("文档解析错误！", e);
        }
        return workbookData;
    }

    /**
     * 表单处理器。
     *
     * @author dengb
     */
    public class SheetHandler extends DefaultHandler {
        /**
         * 保存一张表单中所有的数据，不会保存空单元格的数据。
         */
        private List<Map<String, String>> sheetData;
        /**
         * 保存一行的数据，不会保存空单元格的数据。
         * 数据格式为：{列名-内容}，例如：{A-姓名}。
         */
        private Map<String, String> rowData;
        /**
         * 当单元格的数据为字符串时，其v标签保存了字符串在共享字符串表中的索引。
         */
        private SharedStringsTable sst;
        /**
         * 当c标签拥有s属性时，此属性值为单元格格式表中的索引。
         */
        private StylesTable st;
        /**
         * 上一次的内容。
         */
        private String lastContents;
        /**
         * 上一次的单元格引用。
         */
        private String lastR;
        /**
         * 单元格的数据类型。
         */
        private CellType cellType;
        /**
         * 单元格格式编号。
         * @see org.apache.poi.ss.usermodel.BuiltinFormats
         */
        private short formatIndex;
        /**
         * 单元格格式。
         * @see org.apache.poi.ss.usermodel.BuiltinFormats#getBuiltinFormat(int)
         */
        private String formatString;
        /**
         * 数据格式化器。
         */
        private final DataFormatter formatter;

        public SheetHandler(SharedStringsTable sst, StylesTable st) {
            this.sst = sst;
            this.st = st;
            this.formatter = new DataFormatter();
        }

        @Override
        public void startDocument() throws SAXException {
            sheetData = new ArrayList<>();
            rowData = new HashMap<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("row")) {
                rowData.clear();
            }
            /*
            当单元格为空时，将不存在c标签。
            通过检测英文字符的连贯性确定是否有空单元格。
             */
            if (qName.equals("c")) {    // c => cell
                lastR = attributes.getValue("r");    // r => cell reference
                String s = attributes.getValue("s");    // s => style
                String t = attributes.getValue("t");    // t => type

                if (t != null) {
                    switch (t) {
                        case "b":
                            cellType = CellType.BOOLEAN;
                            break;
                        case "e":
                            cellType = CellType.ERROR;
                            break;
                        case "str":
                            cellType = CellType.INLINE;
                            break;
                        case "s":
                            cellType = CellType.STRING;
                            break;
                    }
                } else {
                    cellType = CellType.GENERAL;
                }
                if (s != null && t == null) {
                    CellStyle cellStyle = st.getStyleAt(Integer.parseInt(s));
                    formatIndex = cellStyle.getDataFormat();
                    formatString = cellStyle.getDataFormatString();

                    if (formatString == null) {
                        cellType = CellType.UNKNOWN;
                        formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
                    }

                    if (formatString.equals("m/d/yy")) {
                        cellType = CellType.DATE;
                    }
                }
            }
            if (qName.equals("f")) {
                cellType = CellType.FORMULA;
            }

            lastContents = "";  // 清除上一个单元格的数据，为当前单元格做准备。
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // 根据SST的索引值的到单元格的真正要存储的字符串
            // 这时characters()方法可能会被调用多次
            String value = null;
            if (qName.equals("v")) {
                switch (cellType) {
                    case BOOLEAN:
                        value = lastContents.charAt(0) == '0' ? "FALSE" : "TRUE";
                        break;
                    case ERROR:
                        value = lastContents;
                        break;
                    case FORMULA:
                        value = lastContents;
                        break;
                    case INLINE:
                        value = new XSSFRichTextString(lastContents).getString().trim();
                        break;
                    case STRING:
                        value = new XSSFRichTextString(sst.getEntryAt(Integer.parseInt(lastContents))).getString().trim();
                        break;
                    case DATE:
                        value = DateFormatUtils.format(DateUtil.getJavaDate(Double.parseDouble(lastContents)), "yyyy-MM-dd");
                        break;
                    case DATETIME:
                        // not implement
                        break;
                    case UNKNOWN:
                        value = new XSSFRichTextString(lastContents).getString().trim();
                        break;
                    default:
                        if (formatString != null) {
                            value = formatter.formatRawCellContents(Double.parseDouble(lastContents), formatIndex, formatString);
                        } else {
                            value = lastContents;
                        }
                }
                rowData.put(new CellReference(lastR).getColName(), value);
            }
            if (qName.equals("row")) {
                sheetData.add(new HashMap<>(rowData));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            lastContents += new String(ch, start, length);  // 获取单元格的内容
        }

        @Override
        public void endDocument() throws SAXException {
            workbookData.addAll(sheetData);
        }
    }
}
