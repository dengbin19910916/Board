package com.wrox.utils.excel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrox.utils.excel.annotation.Sheet;
import com.wrox.utils.excel.annotation.Title;
import com.wrox.utils.excel.annotation.Workbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dengbin on 2015/10/4.
 */
public class AnnotationParser {
    private static final Logger log = LogManager.getLogger();

    public AnnotationParser() {
        super();
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
    public <T> void parse(Class<T> clazz) throws NoSuchMethodException, IOException, IllegalAccessException, InstantiationException {





        // 将注解解析成为以下格式
        // Map<String, List<Map<String, Map<String, String>>>>
        /*
         {"行员管理[WorkbookName]": [{"城西支行[SheetName](Clerk)": [{"行员代号[HeaderName]": "id"},{"行员名称[HeaderName]": "name"}]}, {"城东支行[SheetName](Clerk)": [{"行员代号[HeaderName]": "id"},{"行员名称[HeaderName]": "name"}]}]}
          */
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

        Map<String, String> sheetDictionary = new HashMap<>();
        Map<String, String> titleDictionary = new HashMap<>();

        Workbook workbook = clazz.getAnnotation(Workbook.class);
        String workbookName = workbook.name();

        Sheet[] sheets = workbook.sheets();
        for (Sheet sheet : sheets) {
            String sheetName = sheet.name();
            Class<?> sheetMapper = sheet.mapper() == Object.class ? clazz : sheet.mapper();
            System.out.println((String) sheetMapper.newInstance());
        }
        dictionary.put(workbookName, new ArrayList<>());

        /*Map<String, List<Map<String, Map<String, String>>>> dictionary = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        dictionary =  mapper.readValue(src, dictionary.getClass());
        System.out.println("==>" + dictionary);

        System.out.println(clazz.getSimpleName());
        Map<String, Object> workbookDictionary = new HashMap<>();
        Map<String, String> sheetDictionary = new HashMap<>();
        Map<String, String> titleDictionary = new HashMap<>();

        Workbook workbook = clazz.getAnnotation(Workbook.class);
        String workbookName = workbook.name();
        workbookDictionary.put("name", workbook.name());
        Sheet[] sheets = workbook.sheets();
        workbookDictionary.put("sheets", sheets);
        String[] sheetNames = new String[sheets.length];
        for (int i = 0; i < sheets.length; i++) {
            sheetNames[i] = sheets[i].name();
        }
        Workbook.Type type = workbook.type();
        workbookDictionary.put("type", type);

        System.out.println(workbookName + "-" + Arrays.toString(sheetNames) + "-" + type);
        System.out.println(workbookDictionary);*/
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

        AnnotationParser parser = new AnnotationParser();
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
