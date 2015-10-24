package com.wrox.utils.excel;

import com.wrox.utils.excel.annotation.ExcelColumn;
import com.wrox.utils.excel.annotation.ExcelSheet;
import com.wrox.utils.excel.annotation.ExcelType;
import com.wrox.utils.excel.annotation.ExcelWorkbook;

import java.time.LocalDate;
import java.util.Date;

/**
 * 行员
 *
 * Created by Dengbin on 2015/10/3.
 */
@ExcelWorkbook(
        value = "行员管理",
        sheets = {
                @ExcelSheet("城西支行"),
                @ExcelSheet("城东支行")
        },
        type = ExcelType.XLSX
)
public class Clerk {

    @ExcelColumn("行员代号")
    private String clerkId;
    @ExcelColumn("行员名称")
    private String name;
    @ExcelColumn("身份证")
    private String id;
    @ExcelColumn(value = "出生日期", exportable = false)
    private LocalDate birthday;
    @ExcelColumn(value = "业务量", importable = false)
    private double portfolio;
    @ExcelColumn(value = "绩效", importable = false)
    private int performance;
    @ExcelColumn(value = "发放日期", importable = false, exportable = false)
    private Date issueDate;
    @ExcelColumn(value = "备注")
    private String comment;
    @ExcelColumn("离职标识")
    private Boolean flag;

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public double getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(double portfolio) {
        this.portfolio = portfolio;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Clerk{" +
                "clerkId='" + clerkId + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", birthday=" + birthday +
                ", portfolio=" + portfolio +
                ", performance=" + performance +
                ", issueDate=" + issueDate +
                ", comment='" + comment + '\'' +
                ", flag=" + flag +
                '}';
    }
}
