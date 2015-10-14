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
    private String clerkId;
    @Title("行员名称")
    private String name;
    @Title("身份证")
    private String id;
    @Title(value = "出生日期", exportable = false)
    private LocalDate birthday;
    @Title(value = "业务量", importable = false)
    private int portfolio;
    @Title(value = "绩效", importable = false)
    private int performance;
    @Title(value = "发放日期", importable = false, exportable = false)
    private LocalDate issueDate;
    @Title(value = "备注")
    private String comment;

    public Clerk() {
        super();
    }

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

    public int getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(int portfolio) {
        this.portfolio = portfolio;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
