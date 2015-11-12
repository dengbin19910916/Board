package com.wrox.utils.excel.operator;

import com.wrox.Test;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.examples.EventExample;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 07及以上版本的Excel工作簿的读取，采用事件处理方式。
 *
 * Created by dengb on 2015/11/5.
 * @author dengb
 * @version 1.0
 */
public class HSSFExcelReader implements ExcelReader {

    private HSSFEventHandler handler;

    @Override
    public List<Map<String, String>> readSheets(int... sheetIndex) {
        return null;
    }

    public static class HSSFEventHandler implements HSSFListener {
        private SSTRecord sstRec;

        @Override
        public void processRecord(Record record) {
            switch (record.getSid()) {
                case BOFRecord.sid:
                    BOFRecord bof = (BOFRecord) record;
                    switch (bof.getType()) {
                        case BOFRecord.TYPE_WORKBOOK:
                            System.out.println("type workbook");
                            break;
                        case BOFRecord.TYPE_WORKSHEET:
                            System.out.println("sheet reference");
                            break;
                    }
                    break;
                case BoundSheetRecord.sid:
                    BoundSheetRecord bsr = (BoundSheetRecord) record;
                  System.out.println("Sheet named: " + bsr.getSheetname());
                    break;
                case RowRecord.sid:
                    RowRecord rowRec = (RowRecord) record;
                    System.out.println("Row found, first column at " + rowRec.getFirstCol() + " last column at " + rowRec.getLastCol());
                    break;
                case NumberRecord.sid:
                    NumberRecord numRec = (NumberRecord) record;
                    System.out.println("Cell found with value " + numRec.getValue()
                            + " at row " + numRec.getRow() + " and column " + numRec.getColumn());
                    break;
                case SSTRecord.sid:
                    sstRec = (SSTRecord) record;
                    for (int k = 0; k < sstRec.getNumUniqueStrings(); k++) {
                        System.out.println("String table value " + k + " = " + sstRec.getString(k));
                    }
                    break;
                case LabelSSTRecord.sid:
                    LabelSSTRecord lRec = (LabelSSTRecord) record;
                    System.out.println("String cell found with value " + sstRec.getString(lRec.getSSTIndex()));
                    break;
            }
        }

        public static void main(String[] args) throws IOException {
            FileInputStream fin = new FileInputStream(Test.clerk2);
            POIFSFileSystem poifs = new POIFSFileSystem(fin);
            InputStream din = poifs.createDocumentInputStream("Workbook");
            HSSFRequest req = new HSSFRequest();
            req.addListenerForAllRecords(new EventExample());
            HSSFEventFactory factory = new HSSFEventFactory();
            factory.processEvents(req, din);
            fin.close();
            din.close();
//            System.out.println("done.");
        }
    }
}
