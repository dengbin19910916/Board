package com.wrox.utils.excel.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate的类型适配器。
 *
 * @author dengb
 * @version 1.0
 */
public class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private String dateFormat;

    public LocalDateTypeAdapter(String dateFormat) {
        this.dateFormat = dateFormat == null ? "yyyy-MM-dd" : dateFormat;
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(dateFormat));
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(dateFormat)));
    }
}
