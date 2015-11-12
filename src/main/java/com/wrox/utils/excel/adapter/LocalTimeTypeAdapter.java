package com.wrox.utils.excel.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalTime的类型适配器。
 *
 * @author dengb
 * @version 1.0
 */
public class LocalTimeTypeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    private String timeFormat;

    public LocalTimeTypeAdapter(String timeFormat) {
        this.timeFormat = timeFormat == null ? "yyyy-MM-dd" : timeFormat;
    }

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(timeFormat));
    }

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(timeFormat)));
    }
}
