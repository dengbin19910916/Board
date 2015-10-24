package com.wrox.utils.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标识一个对象与Excel表格可以相互映射。
 * <p>
 *
 * @author Dengbin
 *         Created by Dengbin on 2015/10/4.
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface ExcelWorkbook {

    /**
     * Excel文件名称，用于生成Excel文件。
     */
    String value();

    /**
     * Excel中的表单，可能有多张表单，
     * 默认为表单“Sheet1”。<br/>
     * 表单中的数据与Javabean对象进行相互转换。
     */
    ExcelSheet[] sheets() default @ExcelSheet;

    /**
     * Excel文件类型，默认为97-2003工作簿。
     *
     * @see ExcelType#XLS
     */
    ExcelType type() default ExcelType.XLS;
}
