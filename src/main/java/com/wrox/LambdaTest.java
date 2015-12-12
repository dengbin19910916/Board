package com.wrox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

/**
 * @author dengb
 */
public class LambdaTest {


    public static void main(String[] args) throws IOException {
        System.out.println(new SimpleDateFormat("yyyyyyyy-MMMMMM-ddddddd HHHHHH:mmmmmmmm:sssssss hh:mm:ss.SSS a D Z   w   W   F  EEEE", Locale.ENGLISH).format(new Date()));
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getDayOfYear());
    }
}
