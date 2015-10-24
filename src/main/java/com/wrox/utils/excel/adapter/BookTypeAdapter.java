package com.wrox.utils.excel.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wrox.utils.excel.Book;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by dengb on 2015/10/20.
 */
public class BookTypeAdapter extends TypeAdapter<Book> {

    @Override
    public void write(JsonWriter out, Book value) throws IOException {
        out.beginObject();
        out.name("isbn").value(value.getIsbn());
        out.name("title").value(value.getTitle());
        out.name("authors").value(StringUtils.join(value.getAuthors(), ";"));
        out.name("date").value(value.getDate().toString());
        out.endObject();
    }

    @Override
    public Book read(JsonReader in) throws IOException {
        final Book book = new Book();

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            String value = in.nextString();
            switch (name) {
                case "isbn":
                    book.setIsbn(value);
                    break;
                case "title":
                    book.setTitle(value);
                    break;
                case "authors":
                    book.setAuthors(value.split(";"));
                    break;
                case "date":
                    book.setDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())));
                    break;
            }
        }
        in.endObject();

        return book;
    }
}
