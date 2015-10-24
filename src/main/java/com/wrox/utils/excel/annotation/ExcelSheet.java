package com.wrox.utils.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标注一个对象为Excel对象类型，当读取Excel文件后将文件内容转换成此对象类型。
 *
 * Created by Dengbin on 2015/10/4.
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface ExcelSheet {

    /**
     * Excel文件的表单名称。
     */
    String value() default "Sheet1";

    /**
     * 需要进行转换的对象类型。
     */
    Class<?> mapper() default Object.class;
}
