/**
 * 处理Excel文件。<br/>
 * 当文件小于5M时采用普通的对象方式进行处理。
 * 当文件过大时采用事件方式进行处理：
 *     03及其以下版本的Excel工作簿的处理类通过实现HSSFListener，监听Excel的处理事件完成对Excel工作簿的读取工作。
 *     07及其以上版本的Excel工作簿的处理类通过继承DefaultHandler，将Excel工作簿转换成XML文档进行读取处理。
 * 以上处理方式均只能读取Excel工作簿的内容，不能向Excel工作簿中写入数据。
 * 当数据量不大时，可以采用普通的对象操作方式创建Excel文件，当数据量过大时，需要使用SXSSFWorkbook类来创建Excel文件。
 *
 * @author dengb
 * @see org.apache.poi.hssf.eventusermodel.HSSFListener
 * @see org.xml.sax.helpers.DefaultHandler
 * @see com.wrox.utils.excel.operator.ExcelReader
 * @see com.wrox.utils.excel.operator.XSSFExcelReader
 * @see com.wrox.utils.excel.operator.ExcelMapper
 * @see com.wrox.utils.excel.operator.AnnotationExcelMapper
 */
package com.wrox.utils.excel.operator;