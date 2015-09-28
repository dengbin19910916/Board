package com.wrox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dengb on 2015/9/10.
 */
public class Test {

    public static void main(String[] args) {
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

        System.out.println(System.getProperty("user.dir"));
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
        }
    }
}
