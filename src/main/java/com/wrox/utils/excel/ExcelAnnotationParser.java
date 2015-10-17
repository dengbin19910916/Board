package com.wrox.utils.excel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrox.utils.excel.annotation.Title;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Dengbin on 2015/10/4.
 */
public class ExcelAnnotationParser {
    private static final Logger log = LogManager.getLogger();

    public ExcelAnnotationParser() {
        super();
    }

    public Map<String, Class> getTypeDirectory(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Class> directory = new HashMap<>();
        for (Field field : fields) {
            Title title = field.getAnnotation(Title.class);
            if (Objects.nonNull(title)) {
                directory.put(title.value(), getWrapType(field.getType()));
            }
        }
        return directory;
    }

    public static Class getWrapType(Class clz) {
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
     * @see com.wrox.utils.excel.annotation.Title
     */
    public <T> Method[] getSetMethod(Class<T> clazz) {
        List<Method> methods = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields(); // 获取所有定义的字段
        Map<String, Class> methodParameter = new HashMap<>();
        for (Field field : fields) {    // 获取所有的set方法
            Title title = field.getDeclaredAnnotation(Title.class);
            if (title != null) {
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
        System.out.println(methodParameter);

        return methods.toArray(new Method[methods.size()]);
    }

    /**
     * @param clazz
     * @param <T>
     * @throws NoSuchMethodException
     */
    public <T> Map<String, Map<String, Object>> parse(Class<T> clazz) throws NoSuchMethodException, IOException, IllegalAccessException, InstantiationException {
        

        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, IOException, InstantiationException {
        String src = "{\n" +
                "           \"行员管理[WorkbookName]\": [\n" +
                "             {\n" +
                "               \"城西支行[SheetName](Clerk)\": [\n" +
                "                 {\"行员代号[HeaderName]\": \"id\"},\n" +
                "                 {\"行员名称[HeaderName]\": \"name\"}\n" +
                "               ]\n" +
                "             }, {\n" +
                "               \"城东支行[SheetName](Clerk)\": [\n" +
                "                 {\"行员代号[HeaderName]\": \"id\"},\n" +
                "                 {\"行员名称[HeaderName]\": \"name\"}\n" +
                "               ]\n" +
                "             }\n" +
                "           ]\n" +
                "         }";

        Map<String, List<Map<String, Map<String, String>>>> dictionary = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        dictionary =  mapper.readValue(src, dictionary.getClass());
        System.out.println("==>" + dictionary);

        ExcelAnnotationParser parser = new ExcelAnnotationParser();
//        parser.parse(Clerk.class);
        parser.getSetMethod(Clerk.class);

        /*ExcelReader reader = new DefaultExcelReader();
        Clerk clerk1 = reader.readContent(new XSSFWorkbook(new FileInputStream("C:\\WorkSpace\\test2.xlsx")), Clerk.class);

//        Clerk clerk = new Clerk("730001", "邓斌", "1991-09-16");
//        Clerk clerk = new Clerk();
//        Class clazz = clerk.getClass();
        Class clazz = Clerk.class;
        Annotation[] annotations = clazz.getAnnotations();
        System.out.println(Arrays.toString(annotations));

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Title title = field.getDeclaredAnnotation(Title.class);
            if (title != null) {
                System.out.println(title.value() + "--" + field.getName());
            }
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Title title = method.getAnnotation(Title.class);
            if (title != null) {
                System.out.println(title.value() + "--" + method.getName());
            }
        }*/
        /*Field field = clazz.getField("name");
        Annotation[] fa = field.getAnnotations();
        System.out.println(Arrays.toString(fa));*/
        //http://pan.baidu.com/s/1AamTg

//        Class<?> cla = clerk.getClass();
//        Annotation[] info = cla.getMethod("getName").getAnnotations();
//        System.out.println(Arrays.asList(info));

    }
}
