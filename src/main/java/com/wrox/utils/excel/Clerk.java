package com.wrox.utils.excel;

import com.wrox.utils.excel.annotation.Sheet;
import com.wrox.utils.excel.annotation.Title;
import com.wrox.utils.excel.annotation.Workbook;

import java.time.LocalDate;

/**
 * 行员
 *
 * Created by Dengbin on 2015/10/3.
 */
@Workbook(
        name = "行员管理",
        sheets = {
                @Sheet(name = "城西支行"),
                @Sheet(name = "城东支行")
        },
        type = Workbook.Type.XLSX
)
//@Workbook(name = "行员管理")
public class Clerk {

    @Title("行员代号")
    private String id;
    @Title("行员名称")
    private String name;
    private String birthday;
//    @Title(value = "入行日期", type = LocalDate.class)
//    private LocalDate intoDate;

    private LocalDate localDate;

    public Clerk() {
        super();
    }

    public Clerk(String id, String name, String birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    @Title(value = "出生日期")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Clerk{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
