package com.wrox;

import com.wrox.utils.ExcelUtils;
import com.wrox.utils.excel.Excels;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import static com.wrox.utils.ExcelUtils.ExcelVersion.HIGH;
import static com.wrox.utils.ExcelUtils.ExcelVersion.LOW;

/**
 * Created by dengb on 2015/9/10.
 */
public class Test {

    private static String path = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\行员管理.xlsx";

    public static void main(String[] args) throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
//        System.out.println(NumberUtils.isDigits("1231"));

        InputStream inputStream = new FileInputStream(path);
        Excels.read(inputStream);

//        BigInteger integer = null;
//        System.out.println(new BigDecimal("1213.00123").precision());

        /*Workbook workbook = WorkbookFactory.create(new FileInputStream(path));
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(6);
        org.apache.poi.ss.usermodel.Cell cell = row.getCell(0);
        double d = cell.getNumericCellValue();
        System.out.println(d + "------" + Double.MAX_VALUE);*/
//        System.out.println(cell.getNumericCellValue());


//        System.out.println(Double.TYPE);

//        Number number = NumberUtils.createNumber("127.11");
//        System.out.println(number.byteValue());

//        Class clazz = Integer.class;
//        Class classes = clazz.getSuperclass();
//        System.out.println(classes == Number.class);

//        System.out.println(NumberUtils.createInteger("123"));
//        System.out.println(NumberUtils.isDigits("123"));

//        Class cla = Class.forName(Integer.class.getName());
//        Object i = cla.cast(111);
//        System.out.println(i);

        /*double d = 10000.01;
        int i = (int) d;
        System.out.println(i);*/

//        System.out.println(inputStream.);

        /*LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(dateFormat.format(localDateTime));
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));*/

//        InputStream inputStream = new PushbackInputStream(new FileInputStream(path));


        /*Map<String, Map<String, Collection<Map<String, Object>>>> wb = new LinkedHashMap<>();
        Map<String, Object> clerk1 = new LinkedHashMap<>();
        clerk1.put("id", "730001");
        clerk1.put("name", "张三");
        clerk1.put("birthday", LocalDate.parse("1991-09-16"));
        Map<String, Object> clerk2 = new LinkedHashMap<>();
        clerk2.put("id", "730002");
        clerk2.put("name", "李四");
        clerk2.put("birthday", LocalDate.parse("1990-11-30"));
        Collection<Map<String, Object>> list = new ArrayList<>();
        list.add(clerk1);
        list.add(clerk2);

        Map<String, Collection<Map<String, Object>>> sheet = new LinkedHashMap<>();
        sheet.put("城西支行", list);

        Map<String, Object> clerk3 = new LinkedHashMap<>();
        clerk3.put("id", "730003");
        clerk3.put("name", "王二");
        clerk3.put("birthday", LocalDate.parse("2000-05-11"));
        Map<String, Object> clerk4 = new LinkedHashMap<>();
        clerk4.put("id", "730003");
        clerk4.put("name", "麻子");
        clerk4.put("birthday", LocalDate.parse("2010-01-22"));
        Collection<Map<String, Object>> list2 = new ArrayList<>();
        list2.add(clerk3);
        list2.add(clerk4);
        sheet.put("城东支行", list2);

        wb.put("行员管理", sheet);

        Gson gson = new Gson();
        String json = gson.toJson(wb);
//        System.out.println(json);

        LocalDate localDate = LocalDate.now();
        String dateJson = gson.toJson(localDate);
//        System.out.println(dateJson);
        LocalDate localDate1 = gson.fromJson(dateJson, new TypeToken<LocalDate>() {}.getType());
//        System.out.println(localDate1);

        Map<String, Map<String, Collection<Clerk>>> maps =  gson.fromJson(json, new TypeToken<Map<String, Map<String, Collection<Clerk>>>>() {}.getType());
        System.out.println(maps);

        Map<String, Map<String, Collection<Map<String, Object>>>> workbook = gson.fromJson(json, new TypeToken<Map<String, Map<String, Collection<Map<String, Object>>>>>() {}.getType());
        Collection<Map<String, Object>> collection = workbook.get("行员管理").get("城西支行");
        Iterator<Map<String, Object>> iterator = collection.iterator();
        Map<String, Object> map = iterator.<Map<String, Object>>next();*/
//        System.out.println(((Map) map.get("出生日期")).get("year"));


//        Workbook.Type type = Workbook.Type.getType(inputStream);
//        System.out.println(type);

//        org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(inputStream);
//        System.out.println(workbook);

//        UUID uuid = UUID.randomUUID();
//        System.out.println(uuid);

//        System.out.println(Theme.MATERIAL_BLACK);

//        Path path = Paths.get(DefaultTicketService.BASE_PATH, "123.jsp");
//        System.out.println(path);

//        NIOOperDemo nood = new NIOOperDemo();
//        String fileName="c:\\hello.txt";
//        //nood.readFileByIO(fileName);
//        //nood.readByNIO(fileName);
//        nood.writeFileByNIO(fileName);

        /*Path path = Paths.get("C:\\WorkSpace\\Upload\\hello.txt");
        System.out.println(path);
        Files.exists(path);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

//        System.out.println("=> " + System.getProperty("user.dir"));

//        Path path = Paths.get("C:\\\\WorkSpace\\\\Upload\\\\hello.txt");
//        System.out.println(path.toUri());

//        System.out.println(System.getProperty("user.dir") + "\\..");

//        String dir = "upload*att";
//        String[] dirs = dir.split("[<>/\\\\|:\"*?]");
//        System.out.println(Arrays.toString(dirs));
//        String s = "<12  < <>34 5<.>67 >< 89>>";
//        System.out.println(s.replaceAll("[<> ]", ""));
//        System.out.println(String.format("upload.%s.ticket.attachment", "dengbin"));
//        Path path = Paths.get("", "Dengbin.ticket.attachment");
//        String dir = "Dengbin.ticket.attachment";
//        String[] dirs = dir.split("[./\\\\|:\"*?]");
//        System.out.println(dir);
//        Path path = Paths.get("C:\\Tools\\Server\\apache-tomcat-8.0.26\\bin\\..\\board\\Dengbin\\ticket\\attachment");
//        System.out.println(path.getFileName());
        /*try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        System.out.println(System.getProperty("os.name"));
//        System.out.println(SystemUtils.OSType.UNIX);
//        System.out.println(SystemUtils.OSType.valueOf("WINDOWS"));
//        System.out.println(SystemUtils.getOSType());

        /*System.out.println(System.getProperty("user.dir"));
        try {
            Path file = Paths.get("Upload\\a\\hello.txt");
            System.out.println(file.getParent());
            if (!Files.exists(file.getParent())) {
                Files.createDirectories(file.getParent());
            }
            System.out.println(Files.deleteIfExists(file));
            Files.createFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*String filepath = "scott/ticket\\attachment.a.txt <> ";
        filepath = filepath.replaceAll("[<> ]", "");
//        System.out.println(filepath);
        String[] ds = filepath.split("[./\\\\|:\"*?]").clone(); // problem : 需要截取出文件名。
//        System.out.println(Arrays.toString(ds));


        int fileTypeIndex = filepath.lastIndexOf(".");
//        System.out.println(fileTypeIndex);
        String path = filepath.substring(0, fileTypeIndex).replaceAll("[./\\\\|:\"*?]", "\\\\");
        String type = filepath.substring(fileTypeIndex);
//        int fileNameIndex = path.la
//        System.out.println(path);
        System.out.println(path + "<-->" + type);
        int fileNameIndex = path.lastIndexOf("\\");
        String dire = path.substring(0, fileNameIndex);
        String filename = path.substring(fileNameIndex + 1);
        System.out.println(dire + "<-->" + filename);
//        System.out.println("scott.ticket.attachment.a.txt <> ".contains("<"));*/

//        System.out.println(SystemUtils.getOSType());

//        System.out.println(FileUtils.getDirectory("scott/ticket\\attachment.a.txt"));
//        System.out.println(FileUtils.getFilename("scott/ticket\\attachment.a.txt"));

//        System.out.println(Boolean.class);

        /*String path ="C:\\WorkSpace\\test2.xlsx";
        File file = new File(path);
//        InputStream is = new FileInputStream(file);
//        System.out.println(isExcel2003(is));
//        System.out.println(isExcel2007(is));

        System.out.println(isExcel2003(new FileInputStream(file)));
        System.out.println(isExcel2007(new FileInputStream(file)));*/

//        System.out.println(FileUtils.getFilename("b\\a.txt"));
//        System.out.println(SystemUtils.getPathRegex());

//        System.out.println(LocalDate.now());

//        Workbook.ExcelVersion

        /*String excelPath = "C:\\WorkSpace\\test2.xlsx";
        InputStream inputStream = new FileInputStream(excelPath);
        *//*try {
            new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            System.out.println("into exception");
            new HSSFWorkbook(inputStream);
        }*//*

//        Workbook workbook = WorkbookFactory.create(inputStream);
        Workbook workbook = ExcelUtils.getWorkbook(inputStream);
        System.out.println(workbook);*/

//        System.out.println(LocalDate.parse("2015-01-12"));

//        Instant instant = Instant.ofEpochSecond(new Date().getTime() / 1000, new Date().getTime() % 1000);
//        System.out.println(instant);

//        System.out.println(LocalDateTime.ofEpochSecond(new Date().getTime() / 1000, (int) (new Date().getTime() % 1000), ZoneOffset.UTC));

//        DateFormat formatter = new SimpleDateFormat("yyyy-MMMM-dd");
//        System.out.println(formatter.format(new Date()));
//        LocalDate localDate = LocalDate.parse("2015-十月-05", DateTimeFormatter.ofPattern("yyyy-MMMM-dd"));
//        System.out.println(localDate);

//        Class cla = String.class;
//        System.out.println(cla == String.class);

        /*System.out.println(String.class.getName());
        try {
            String s = (String) Class.<String>forName("java.lang.String").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        /*Class<Clerk> cla = Clerk.class;
        Object obj = cla.newInstance();

//        Method method = cla.getMethod("setId", String.class);
//        method.invoke(obj, "111");

        Field[] fields = cla.getDeclaredFields();
        System.out.println(Arrays.toString(fields));
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(obj, "111");
        }

        *//*Method[] methods = cla.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                method.invoke(obj, "111");
            }
        }*//*

        System.out.println(obj);*/


    }

    public static ExcelUtils.ExcelVersion isExcel2003(InputStream is) {
        try {
            new HSSFWorkbook(is);
        } catch (Exception e) {
            return ExcelUtils.ExcelVersion.HIGH;
        }
        return ExcelUtils.ExcelVersion.LOW;
    }



    public static ExcelUtils.ExcelVersion isExcel2007(InputStream is) {
        try {
            new XSSFWorkbook( is);
        } catch (Exception e) {
            return HIGH;
        }
        return LOW;
    }
}
