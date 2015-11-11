package com.wrox.utils.excel.operator;

import com.google.gson.*;
import com.wrox.utils.excel.ExcelAnnotationUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过Javabean的注解设置字段与Excel表单的表头的对应信息。
 *
 * @author dengb
 * @version 1.0
 */
public class AnnotationExcelMapper implements ExcelMapper {

    /**
     * 数据采用Gson进行序列化与反序列化。
     */
    private Gson gson;

    /**
     * 构造默认匹配器。
     */
    public AnnotationExcelMapper() {
        this("yyyy-MM-dd", "HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 构造特定日期格式的匹配器。
     *
     * @param dateFormat 日期格式
     */
    public AnnotationExcelMapper(String dateFormat) {
        this(dateFormat, "HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 构造特定日期格式的匹配器。
     *
     * @param dateFormat 日期格式
     * @param timeFormat 时间格式
     */
    public AnnotationExcelMapper(String dateFormat, String timeFormat) {
        this(dateFormat, timeFormat, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 构造特定日期格式的匹配器。
     *
     * @param dateFormat 日期格式
     * @param timeFormat 时间格式
     * @param datetimeFormat 时间戳格式
     */
    public AnnotationExcelMapper(String dateFormat, String timeFormat, String datetimeFormat) {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
//                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (json, typeOfT, context) ->
                                new JsonPrimitive(json.format(DateTimeFormatter.ofPattern(dateFormat))))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                                LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(dateFormat)))
                .registerTypeAdapter(LocalTime.class,
                        (JsonSerializer<LocalTime>) (json, typeOfT, context) ->
                                new JsonPrimitive(json.format(DateTimeFormatter.ofPattern(timeFormat))))
                .registerTypeAdapter(LocalTime.class,
                        (JsonDeserializer<LocalTime>) (json, typeOfT, context) ->
                                LocalTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(timeFormat)))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (json, typeOfT, context) ->
                                new JsonPrimitive(json.format(DateTimeFormatter.ofPattern(datetimeFormat))))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(datetimeFormat)))
                .create();
    }

    @Override
    public <T> T[] match(Class<T> clazz, List<Map<String, String>> contents) {
        Map<String, String> directory = ExcelAnnotationUtils.getNameDirectory(clazz);
        Map<String, String> header = contents.remove(0);

        Map<String, String> map = new HashMap<>(header.size());
        for (Map.Entry<String, String> entry : header.entrySet()) {
            map.put(entry.getKey(), directory.get(entry.getValue()));
        }

        List<Map<String, String>> list = new ArrayList<>();
        for (Map<String, String> content : contents) {
            Map<String, String> m = new HashMap<>(content.size());
            for (Map.Entry<String, String> entry : content.entrySet()) {
                m.put(map.get(entry.getKey()), entry.getValue());
            }
            list.add(m);
        }
        // type safe
        return gson.fromJson(gson.toJson(list), (Type) Array.newInstance(clazz, 0).getClass());
    }
}
