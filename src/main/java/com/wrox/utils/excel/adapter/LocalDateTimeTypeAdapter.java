package com.wrox.utils.excel.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime的类型适配器。
 *
 * @author dengb
 * @version 1.0
 */
public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private String dateTimeFormat;

    public LocalDateTimeTypeAdapter(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat == null ? "yyyy-MM-dd" : dateTimeFormat;
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(dateTimeFormat));
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(dateTimeFormat)));
    }
}
