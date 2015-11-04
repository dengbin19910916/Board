package com.wrox;

import com.wrox.utils.ExcelUtils;
import com.wrox.utils.excel.Clerk;
import com.wrox.utils.excel.Excels;
import com.wrox.utils.excel.operator.CellDataType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;

import static com.wrox.utils.ExcelUtils.ExcelVersion.HIGH;
import static com.wrox.utils.ExcelUtils.ExcelVersion.LOW;

/**
 * Created by dengb on 2015/9/10.
 */
public class Test {

    public static final String clerk = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\行员管理.xlsx";

    public static final String book = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\书籍管理.xlsx";

    public static final String ftp = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\客户经理.xlsx";
    public static final String credit = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\信贷数据.xlsx";
    public static final String simple = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\信贷数据simple.xlsx";

    /**
     * See org.xml.sax.helpers.DefaultHandler javadocs
     */
    private static class StyleHandler extends DefaultHandler {
        private SharedStringsTable sst;
        private StylesTable st;
        private String lastContents;
        private boolean nextIsString;
        private CellDataType cellDataType;

        private boolean isTElement;

        private StyleHandler(SharedStringsTable sst, StylesTable st) {
            this.sst = sst;
            this.st = st;
        }

        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
//            System.out.println("==>" + name + " - " + attributes.getValue("v"));
            if (name.equals("c")) { // 单元格需要设置数据类型
                System.out.print(attributes.getValue("r") + " - ");
                String cellType = attributes.getValue("t");
                nextIsString = cellType != null && cellType.equals("s");
//                setNextDataType(attributes);
            }

            if (name.equals("f")) {
                cellDataType = CellDataType.FORMULA;
            }

            isTElement = name.equals("t");
            lastContents = "";  // 清空数据
        }

        /*private void setNextDataType(Attributes attributes) {
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("=>" + attributes.getValue(i));
            }

            String cellType = attributes.getValue("t"); // 日期、数值和公式没有t属性
            if (Objects.nonNull(cellType)) {
                switch (cellType) {
                    case "b":
                        cellDataType = CellDataType.BOOL;
                        break;
                    case "e":
                        cellDataType = CellDataType.ERROR;
                        break;
                    case "inlineStr":
                        cellDataType = CellDataType.INLINE_STR;
                        break;
                    case "s":
                        cellDataType = CellDataType.SST_INDEX;
                        break;
                }
                System.out.println("cell type = " + cellType);
            }
        }*/

        public void endElement(String uri, String localName, String name) throws SAXException {
            if(nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                nextIsString = false;
            }

            if(name.equals("v")) {
                System.out.println(lastContents + " - " + cellDataType);
                cellDataType = null;
            }
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            lastContents += new String(ch, start, length);
        }
    }

    public static void main(String[] args) throws IOException, OpenXML4JException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, SAXException, ParseException {
        // 使用事件处理Excel
       /* OPCPackage pkg = OPCPackage.open(book);
        XSSFReader reader = new XSSFReader(pkg);
        SharedStringsTable sst = reader.getSharedStringsTable();
        StylesTable st = reader.getStylesTable();
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(new StyleHandler(sst, st));
        parser.parse(new InputSource(reader.getSheet("rId1")));*/
//        parser.parse(new InputSource(reader.getStylesData()));


        /*System.out.println("-------------------------");
        System.out.println(BuiltinFormats.getBuiltinFormat(177));

        System.out.println("-------------------------");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String s = numberFormat.format(123456789123456789.123456);
        System.out.println(s);

        System.out.println(numberFormat.parse(s));*/

        /*DecimalFormat decimalFormat = new DecimalFormat("#,##0.00_);[Red]\\(#,##0.00\\)");    //#,##0.00_
        double d = -123456.123456789;

        DataFormatter formatter = new DataFormatter();
        formatter.formatRawCellContents(42369, )


        System.out.println(decimalFormat.format(d));*/



//        System.out.println(NumberUtils.isDigits("1231"));

//        InputStream inputStream = new FileInputStream(book);
//        Book[] books = Excels.read(inputStream, Book.class);
//        System.out.println(Arrays.toString(books));

//        InputStream is = new FileInputStream(path);
//        Clerk[] clerks = Excels.read(is, Clerk.class);
//        System.out.println(Arrays.toString(clerks));
        //////////////////////////////////////////////////////////////////////////////////////////////
//        Clerk[] clerks = Excels.read(Clerk.class, Paths.get(clerk).toFile(), 0, 2);
//        Clerk[] clerks = Excels.read(Clerk.class, new SXSSFWorkbook((XSSFWorkbook) WorkbookFactory.create(Paths.get(clerk).toFile()), 1));
        Clerk[] clerks = Excels.read(Clerk.class, WorkbookFactory.create(Paths.get(clerk).toFile()), 1);
//        Book[] books = Excels.read(new FileInputStream(book), Book.class);
//        CustomerManager[] managers = Excels.read(CustomerManager.class, Paths.get(ftp));
//        Credit[] credits = Excels.read(Credit.class, Paths.get(simple));
        final int[] i = {0};
//        Arrays.asList(credits).stream().forEach(clerk -> System.out.println((++i[0]) + "=> " + clerk));
        Arrays.asList(clerks).stream().forEach(clerk -> System.out.println((++i[0]) + "=> " + clerk));
//        Arrays.asList(managers).stream().forEach(manager -> System.out.println((++i[0]) + "=> " + manager));

//        Excels.write(Clerk.class, clerks);
        //////////////////////////////////////////////////////////////////////////////////////////////

//        Excels.write();

//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

//        System.out.println(LocalTime.now());
//        final int[] j = {0};
//        Arrays.asList(books).stream().forEach(book -> System.out.println((++j[0]) + "--> " + book));

//        Double dd = 1.4102401298123E10;
//        DecimalFormat format = new DecimalFormat("0");
//
//        System.out.println(format.format(dd));

//        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
//            @Override
//            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                System.out.println(json + "....");
//                Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
//                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//            }
//        }).setDateFormat("yyyy-MM-dd").create();
        /*Gson gson = new GsonBuilder().create();
        String json = gson.toJson(LocalDateTime.now());
        String date = LocalDateTime.now().toString();
        System.out.println(json + " --- " + date);

        LocalDateTime time = gson.fromJson("{\"date\":{\"year\":2015,\"month\":10,\"day\":20},\"time\":{\"hour\":0,\"minute\":30,\"second\":21,\"nano\":284000000}}", new TypeToken<LocalDateTime>() {}.getType());
        System.out.println(time);*/

        /* 序列化与反序列化
        Book book = new Book();
        book.setIsbn("978-0321336781");
        book.setTitle("Java Puzzlers: Traps, Pitfalls, and Corner Cases");
        book.setAuthors(new String[] {"Joshua Bloch", "Neal Gafter"});
        book.setDate(LocalDate.now());

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,
                (JsonSerializer<LocalDate>) (localDate, type, context) -> new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern("yyyy/M/d"))))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy/M/d")))
                .create();
        String json = gson.toJson(book, Book.class);
        System.out.println("-->" + json);

        String back = "{\"authors\":[\"Joshua Bloch\",\"Neal Gafter\"],\"isbn\":\"978-0321336781\",\"title\":\"Java Puzzlers: Traps, Pitfalls, and Corner Cases\",\"date\":\"2015/10/20\"}";
        Book book1 = gson.fromJson(back, Book.class);
        System.out.println("-->" + book1);*/

        /*Gson gson = new GsonBuilder().registerTypeAdapter(Book.class, new BookTypeAdapter()).create();
        String json = gson.toJson(book);
        System.out.println(json);

        String ss = "{\"isbn\":\"978-0321336781\",\"title\":\"Java Puzzlers: Traps, Pitfalls, and Corner Cases\",\"authors\":\"Joshua Bloch;Neal Gafter\",\"date\":\"2015-10-20\"}";
        Book bo = gson.fromJson(ss, Book.class);
        System.out.println("->" + bo);

        System.out.println(Instant.parse("2015-10-20T05:08:14.481Z"));*/

//        System.out.println(LocalDate.parse("2015-01-09"));

//        BigInteger integer = null;
//        System.out.println(new BigDecimal("1213.00123").precision());

        /*ExcelWorkbook workbook = WorkbookFactory.create(new FileInputStream(path));
        ExcelSheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(2);
        Cell cell = row.getCell(0);
//        boolean b = cell.getBooleanCellValue();
        System.out.println("--->" + Boolean.valueOf("TRUE"));
        System.out.println(cell.getStringCellValue() + " -- " + cell.getCellType());*/
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


//        ExcelWorkbook.Type type = ExcelWorkbook.Type.getType(inputStream);
//        System.out.println(type);

//        org.apache.poi.ss.usermodel.ExcelWorkbook workbook = WorkbookFactory.create(inputStream);
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

//        ExcelWorkbook.ExcelVersion

        /*String excelPath = "C:\\WorkSpace\\test2.xlsx";
        InputStream inputStream = new FileInputStream(excelPath);
        *//*try {
            new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            System.out.println("into exception");
            new HSSFWorkbook(inputStream);
        }*//*

//        ExcelWorkbook workbook = WorkbookFactory.create(inputStream);
        ExcelWorkbook workbook = ExcelUtils.getWorkbook(inputStream);
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
