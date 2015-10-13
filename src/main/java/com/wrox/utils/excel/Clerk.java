package com.wrox.utils.excel;

import com.wrox.utils.excel.annotation.ExcelType;
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
        value = "行员管理",
        sheets = {
                @Sheet("城西支行"),
                @Sheet("城东支行")
        },
        type = ExcelType.XLSX
)
public class Clerk {

    @Title("行员代号")
    private String id;
    @Title("行员名称")
    private String name;
    @Title(value = "出生日期")
    private LocalDate birthday;
    @Title(value = "业务量")
    private int portfolio;
    @Title(value = "绩效")
    private int performance;
    @Title(value = "发放日期")
    private LocalDate issueDate;
    @Title(value = "备注")
    private String comment;



    public Clerk() {
        super();
    }

    public Clerk(String id, String name, LocalDate birthday) {
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

    public LocalDate getBirthday() {
        return birthday;
    }


    public void setBirthday(LocalDate birthday) {
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
}
