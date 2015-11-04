package com.wrox.utils.file;

import com.wrox.Test;
import com.wrox.utils.excel.Credit;
import com.wrox.utils.excel.Excels;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dengb on 2015/10/26.
 */
public class FileUitls {

    public static <T> void generate(File file, T[] data) throws IOException {
        Path path = file.toPath();
        if (Files.deleteIfExists(path)) {
            Files.createFile(path);
        }

        List<T> list = new ArrayList<>(Arrays.asList(data));
        for (int i = 0; i <= 0; i++) {
            list.addAll(Arrays.asList(data));
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (T t : list) {
                writer.write(t.toString());
                writer.newLine();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            reader.lines().forEach(s -> System.out.println("数据读取：" + s));
        }
    }

    private static String filepath = "C:\\WorkSpace\\IdeaProjects\\Board\\file\\credit.txt";

    public static void main(String[] args) throws IOException, InvalidFormatException {
        generate(new File(filepath), Excels.read(Credit.class, Paths.get(Test.simple)));
    }
}
