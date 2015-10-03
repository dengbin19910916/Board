package com.wrox;

import com.wrox.utils.ExcelUtils;
import com.wrox.utils.SystemUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.wrox.utils.ExcelUtils.ExcelVersion.HIGH;
import static com.wrox.utils.ExcelUtils.ExcelVersion.LOW;

/**
 * Created by dengb on 2015/9/10.
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException {
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
        System.out.println(SystemUtils.getPathRegex());
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
