/**
 * 处理Excel文件。<br/>
 * 当文件小于5M时采用普通的对象方式进行处理。
 * 当文件大于5M时采用事件方式进行处理：
 *     03及其以下版本的Excel工作簿的处理类通过实现HSSFListener，监听Excel的处理事件完成对Excel工作簿的读取工作。
 *     07及其以上版本的Excel工作簿的处理类通过继承DefaultHandler，将Excel工作簿转换成XML文档进行读取处理。
 * 以上处理方式均只能读取Excel工作簿的内容，不能向Excel工作簿中写入数据。
 *
 * Created by dengb on 2015/11/4.
 * @author dengb
 * @see org.apache.poi.hssf.eventusermodel.HSSFListener
 */
package com.wrox.utils.excel.operator;