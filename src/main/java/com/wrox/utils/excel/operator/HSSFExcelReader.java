package com.wrox.utils.excel.operator;

import com.wrox.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


/**
 * 03版本的Excel工作簿的读取，采用监听的处理方式。
 *
 * @author dengb
 * @version 1.0
 */
public class HSSFExcelReader implements ExcelReader {
    private static final Logger log = LogManager.getLogger();

    /**
     * Excel工作簿的所有数据集合。
     */
    private List<Map<String, String>> workbookData;

    private HSSFEventHandler handler;

    @Override
    public List<Map<String, String>> readSheets(int... sheetIndex) {
        return null;
    }

    public static class HSSFEventHandler implements HSSFListener {
        private SSTRecord sstrec;

        /**
         * This method listens for incoming records and handles them as required.
         *
         * @param record The record that was found while reading.
         */
        public void processRecord(Record record) {
            switch (record.getSid()) {
                // the BOFRecord can represent either the beginning of a sheet or the workbook
                case BOFRecord.sid:
                    BOFRecord bof = (BOFRecord) record;
                    if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
                        System.out.println("Encountered workbook");
                        // assigned to the class level member
                    } else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
                        System.out.println("Encountered sheet reference");
                    }
                    break;
                case BoundSheetRecord.sid:
                    BoundSheetRecord bsr = (BoundSheetRecord) record;
                    System.out.println("添加表单名称: " + bsr.getSheetname());
                    break;
                case RowRecord.sid:
                    RowRecord rowrec = (RowRecord) record;
                    System.out.println("Row found, first column at "
                            + rowrec.getFirstCol() + " last column at " + rowrec.getLastCol());
                    break;
                case NumberRecord.sid:
                    NumberRecord numrec = (NumberRecord) record;
                    System.out.println("Cell found with value " + numrec.getValue()
                            + " at row " + numrec.getRow() + " and column " + numrec.getColumn());
                    break;
                // SSTRecords store a array of unique strings used in Excel.
                case SSTRecord.sid:
                    sstrec = (SSTRecord) record;
                    for (int k = 0; k < sstrec.getNumUniqueStrings(); k++) {
                        System.out.println("String table value " + k + " = " + sstrec.getString(k));
                    }
                    break;
                case LabelSSTRecord.sid:
                    LabelSSTRecord lrec = (LabelSSTRecord) record;
                    System.out.println("String cell found with value "
                            + sstrec.getString(lrec.getSSTIndex()));
                    break;
            }
        }
    }

    /**
     * Read an excel file and spit out what we find.
     *
     * @param args Expect one argument that is the file to read.
     * @throws IOException When there is an error processing the file.
     */
    public static void main(String[] args) throws IOException {
        // create a new file input stream with the input file specified
        // at the command line
        FileInputStream fin = new FileInputStream(Test.clerk2);
        // create a new org.apache.poi.poifs.filesystem.Filesystem
        POIFSFileSystem poifs = new POIFSFileSystem(fin);
        // get the Workbook (excel part) stream in a InputStream
        InputStream din = poifs.createDocumentInputStream("Workbook");
        // construct out HSSFRequest object
        HSSFRequest req = new HSSFRequest();
        // lazy listen for ALL records with the listener shown above
        req.addListenerForAllRecords(new HSSFEventHandler());
        // create our event factory
        HSSFEventFactory factory = new HSSFEventFactory();
        // process our events based on the document input stream
        factory.processEvents(req, din);
        // once all the events are processed close our file input stream
        fin.close();
        // and our document input stream (don't want to leak these!)
        din.close();
        System.out.println("done.");
    }
}
