package com.wrox.utils.excel.annotation;

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
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface Title {
    String value() default "";

    Class type() default String.class;
}
