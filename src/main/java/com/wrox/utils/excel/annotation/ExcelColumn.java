package com.wrox.utils.excel.annotation;

import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Excel文件的表头，也是数据列的列名。<br/>
 * 使用了此注解的属性必须拥有get/set方法。<br/>
 *
 * Created by Dengbin on 2015/10/4.
 * @author dengb
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface ExcelColumn {
    String value() default "";

    int type() default Cell.CELL_TYPE_STRING;

    /**
     * 设置日期格式，默认为yyyy-MM-dd
     */
    String dateFormat() default "yyyy-MM-dd";

    /**
     * 是否将Excel文件数据转换成对象数据。
     */
    boolean importable() default true;

    /**
     * 是否将对象数据转换成Excel文件数据。
     */
    boolean exportable() default true;
}
