package com.wrox.utils.excel;

import com.wrox.utils.excel.annotation.ExcelColumn;
import com.wrox.utils.excel.annotation.ExcelSheet;
import com.wrox.utils.excel.annotation.ExcelType;
import com.wrox.utils.excel.annotation.ExcelWorkbook;

import java.time.LocalDate;

/**
 * Created by dengb on 2015/10/20.
 */
@ExcelWorkbook(
        value = "书籍管理",
        sheets = {
                @ExcelSheet("Sheet1")
        },
        type = ExcelType.XLSX
)
public class Book {

    private String[] authors;
    @ExcelColumn("出版商")
    private String isbn;
    @ExcelColumn("书名")
    private String title;
    @ExcelColumn("出版日期")
    private LocalDate date;

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
