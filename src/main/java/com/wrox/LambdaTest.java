package com.wrox;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

/**
 * @author dengb
 */
public class LambdaTest {

    public static void main(String[] args) throws IOException {

        System.out.println(new SimpleDateFormat("G  yyyyyyyy-MMMMMM-ddddddd HHHHHH:mmmmmmmm:sssssss hhhhh:mm:ss.SSSSSSS a DDDDD Z   www   WWWWW   F  EEEEEEEEEEEEE --- z : Z", Locale.ENGLISH).format(new Date()));
        System.out.println("===========================");
        System.out.println(new SimpleDateFormat("GGGGGG aaaaaa DDDDD ZZZZZZZZZZ   www   WWWWW   FFFFF  EEEEEEEEEEEEE --- zzzz : ZZZZZZZZZZ", Locale.ENGLISH).format(new Date()));
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getDayOfYear());

        System.out.println("-------------------------------");

        DateFormatSymbols dfs = DateFormatSymbols.getInstance(Locale.ENGLISH);
//        System.out.println(Arrays.toString(dfs.getEras()) + "\n" + Arrays.toString(dfs.getMonths()) + "\n" + Arrays.toString(dfs.getAmPmStrings()) + "\n" + Arrays.toString(dfs.getWeekdays()));
//        System.out.println(Arrays.toString(dfs.getShortMonths()) + "\n" + Arrays.toString(dfs.getShortWeekdays()) + "\n" + Arrays.deepToString(dfs.getZoneStrings()));
    }
}
