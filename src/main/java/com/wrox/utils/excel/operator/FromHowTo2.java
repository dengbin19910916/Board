package com.wrox.utils.excel.operator;

import com.wrox.Test;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * XSSF and SAX (Event API)
 */
public class FromHowTo2 {
    public void processOneSheet(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        // rId2 found by processing the Workbook
        // Seems to either be rId# or rSheet#
        InputStream sheet2 = r.getSheet("rId" + "1");
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    public void processAllSheets(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        StylesTable stylesTable = r.getStylesTable();
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            System.out.println("Processing new sheet:\n");
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
            System.out.println("");
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
//        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        ContentHandler handler = new SheetHandler(sst);
//        ContentHandler handler = new MyHandler();
        parser.setContentHandler(handler);
        return parser;
    }

    /**
     * See org.xml.sax.helpers.DefaultHandler javadocs
     */
    private static class SheetHandler extends DefaultHandler {
        /**
         * 共享字符串表。
         */
        private SharedStringsTable sst;
        /**
         * 上一次的内容。
         */
        private String lastContents;
        /**
         * 字符串标识。
         */
        private boolean nextIsString;
        /**
         * 工作表索引。
         */
        private int sheetIndex;
        /**
         * 行集合。
         */
        private List<String> rowList = new ArrayList<>();
        /**
         * 当前行。
         */
        private int curRow;
        /**
         * 当前列。
         */
        private int curCol;
        /**
         * T元素标识，对应Excel中的T公式。
         */
        private boolean isTElement;
        /**
         * Excel数据处理逻辑。
         */
        private RowHandler rowHandler;
        /**
         * 异常信息，如果为空则表示没有异常信息。
         */
        private String expMsg;
        /**
         * 单元格数据类型，默认为字符串类型。
         */
        private CellDataType nextDataType;
        /**
         * 数据格式化器。
         */
        private final DataFormatter formatter = new DataFormatter();
        private short formatIndex;
        private String formatString;
        /**
         * 单元格
         */
        private StylesTable stylesTable;

        private boolean dateFlag;
        private boolean numericFlag;

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        /**
         * 设置Excel数据处理句柄。
         *
         * @param rowHandler 数据处理句柄。
         */
        public void setRowHandler(RowHandler rowHandler) {
            this.rowHandler = rowHandler;
        }

        public void setNextDataType(Attributes attributes) {
            nextDataType = CellDataType.NUMERIC;
            formatIndex = -1;
            formatString = null;
            String cellType = attributes.getValue("t");
            String cellStyleStr = attributes.getValue("s");
            switch (cellType) {
                case "b":
                    nextDataType = CellDataType.BOOL;
                    break;
                case "e":
                    nextDataType = CellDataType.ERROR;
                    break;
                case "inlineStr":
                    nextDataType = CellDataType.INLINE_STR;
                    break;
                case "s":
                    nextDataType = CellDataType.SST_INDEX;
                    break;
                case "str":
                    nextDataType = CellDataType.FORMULA;
                    break;
            }
            if (Objects.nonNull(cellStyleStr)) {
                int styleIndex = Integer.parseInt(cellStyleStr);
                CellStyle style = stylesTable.getStyleAt(styleIndex);
                formatIndex = style.getDataFormat();
                formatString = style.getDataFormatString();

                if ("m/d/yy".equals(formatString)) {
                    nextDataType = CellDataType.DATE;
                    formatString = "yyyy-MM-dd";
                }

                if (Objects.isNull(formatString)) {
                    nextDataType = CellDataType.NULL;
                    formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
                }
            }
        }

        public String getDataValue(String value, String thisStr) {
            switch (nextDataType) {
                case BOOL:
                    char first = value.charAt(0);
                    thisStr = first == '0' ? "FALSE" : "TRUE";
                    break;
                case ERROR:
                    thisStr = "\"ERROR:" + value + '"';
                    break;
                case FORMULA:
                    thisStr = '"' + value + '"';
                    break;
                case INLINE_STR:
                    RichTextString richTextString = new XSSFRichTextString(value);
                    thisStr = richTextString.getString();
                    richTextString = null;
                    break;
                case SST_INDEX:
                    try {
                        int idx = Integer.parseInt(value);
                        richTextString = new XSSFRichTextString(value);
                        thisStr = richTextString.getString();
                        richTextString = null;
                    } catch (NumberFormatException e) {
                        thisStr = value;
                    }
                    break;
                case NUMERIC:
                    if (Objects.isNull(formatString)) {
                        thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();
                    } else {
                        thisStr = value;
                    }
                    thisStr = thisStr.replace("_", "").trim();
                    break;
                case DATE:
                    thisStr = thisStr.replace(" ", "T");
                    break;
                default:
                    thisStr = "";
                    break;
            }
            return thisStr;
        }

        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {
            // c => cell
            if (name.equals("c")) {
                // 设置单元格格式
                this.setNextDataType(attributes);
                /*// Print the cell reference
//                System.out.print(name + "--" + attributes.getValue("r") + " - ");
                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                if (cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }

                dateFlag = attributes.getValue("s") != null && attributes.getValue("s").equals("1");
                numericFlag = attributes.getValue("s") != null && attributes.getValue("s").equals("2");*/
            }

            isTElement = name.equals("t");

            // Clear contents cache
            lastContents = "";
        }

        public void endElement(String uri, String localName, String name)
                throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if (nextIsString) {
//                System.out.println("last contents: " + lastContents);
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();  // 获取单元格中的数据
                nextIsString = false;
            }

            if (isTElement) {
                String value = lastContents.trim();
                rowList.add(curCol++, value);
            } else if (name.equals("v")) {
                String value = this.getDataValue(lastContents, "");
                rowList.add(curCol++, value);
            } else {
                if (name.equals("row")) {
                    try {
                        rowHandler.getRows(curRow, rowList);
                    } catch (ExcelParseException e) {
                        expMsg = e.getMessage();
                    }
                    rowList.clear();
                    curRow++;
                    curCol = 0;
                }
            }

            /*
            // v => contents of a cell
            // Output after we've seen the string contents
            if(name.equals("v")) {
                String value = lastContents.trim();
                if (dateFlag && NumberUtils.isNumber(value)) {
                    Date date = DateUtil.getJavaDate(Double.valueOf(value));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    lastContents = dateFormat.format(date);
                    dateFlag = false;
                }

                *//*if (numericFlag && NumberUtils.isNumber(value)) {
                    BigDecimal decimal = new BigDecimal(value);
                    lastContents = decimal.setScale(3, BigDecimal.ROUND_HALF_EVEN).toString();
                }*//*

                System.out.println(lastContents);
            }
            if (name.equals("t")) {
                System.out.println("有检测");
            }*/
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            lastContents += new String(ch, start, length);
        }

        public String getExceptionMessage() {
            return this.expMsg;
        }
    }

    public static void main(String[] args) throws Exception {
        FromHowTo2 howto = new FromHowTo2();
        howto.processOneSheet(Test.book);
//        System.out.println("-----------------------------------------------------------------------------------------------");
//        howto.process(Test.book);

//        howto.process(args[0]);
//        howto.process(args[0]);
    }


}


