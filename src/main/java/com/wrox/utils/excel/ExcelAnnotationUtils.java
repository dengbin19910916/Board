package com.wrox.utils.excel;

import com.wrox.utils.excel.annotation.ExcelColumn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Excel注解的处理工具。
 *
 * @author dengb
 * @version 1.0
 */
public class ExcelAnnotationUtils {
    private static final Logger log = LogManager.getLogger();

    public ExcelAnnotationUtils() {
        super();
    }


    /**
     * 返回一个对象中的字段名称字典。
     * 其字典内容是通过解析被标注了@ExcelColumn的字段获得的。
     * 格式为：{englishName: chineseName}。
     *
     * @param clazz 需要被解析并获取字段名称字典的对象
     * @return 类型字段
     */
    public static Map<String, String> getNameDirectory(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> directory = new HashMap<>();
        for (Field field : fields) {
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            if (Objects.nonNull(column)) {
                directory.put(column.value(), field.getName());
            }
        }
        return directory;
    }

    /**
     * 返回一个对象中的字段类型字典。
     * 其字典内容是通过解析被标注了@ExcelColumn的字段获得的。
     *
     * @param clazz 需要被解析并获取字段类型字典的对象
     * @return 类型字段
     */
    public static Map<String, Class> getTypeDirectory(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Class> directory = new HashMap<>();
        for (Field field : fields) {
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            if (Objects.nonNull(column)) {
                directory.put(column.value(), getWrapType(field.getType()));
            }
        }
        return directory;
    }

    /**
     * 返回基本类型的包装类型。
     *
     * @param clz 基本类型。
     * @return 对应的包装类型。
     */
    private static Class getWrapType(Class clz) {
        if (clz == byte.class) {
            return Byte.class;
        }
        if (clz == short.class) {
            return Short.class;
        }
        if (clz == int.class) {
            return Integer.class;
        }
        if (clz == long.class) {
            return Long.class;
        }
        if (clz == float.class) {
            return Float.class;
        }
        if (clz == double.class) {
            return Double.class;
        }
        if (clz == char.class) {
            return Character.class;
        }
        if (clz == boolean.class) {
            return Boolean.class;
        }
        return clz;
    }

    /**
     * 返回类中所有定义字段并被Title注解的的set方法。
     *
     * @param clazz 源类
     * @param <T> 泛型类型
     * @return 方法数组
     * @see ExcelColumn
     */
    public static <T> Method[] getSetMethod(Class<T> clazz) {
        List<Method> methods = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields(); // 获取所有定义的字段
        Map<String, Class> methodParameter = new HashMap<>();
        for (Field field : fields) {    // 获取所有的set方法
            ExcelColumn column = field.getDeclaredAnnotation(ExcelColumn.class);
            if (column != null) {
                methodParameter.put("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), field.getType());
            }
        }
        for (Map.Entry<String, Class> entry : methodParameter.entrySet()) {
            try {
                methods.add(clazz.getDeclaredMethod(entry.getKey(), entry.getValue()));
            } catch (NoSuchMethodException e) {
                log.warn("方法[{}]不存在！", entry.getKey());
                return null;
            }
        }

        return methods.toArray(new Method[methods.size()]);
    }
}
