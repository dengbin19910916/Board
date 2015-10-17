package com.wrox.utils.excel;

import com.wrox.utils.excel.annotation.ExcelType;
import com.wrox.utils.excel.annotation.Sheet;
import com.wrox.utils.excel.annotation.Title;
import com.wrox.utils.excel.annotation.Workbook;

import java.math.BigInteger;
import java.time.LocalDateTime;

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
    private LocalDateTime birthday;
    @Title(value = "业务量", importable = false)
    private BigInteger portfolio;
    @Title(value = "绩效", importable = false)
    private int performance;
    @Title(value = "发放日期", importable = false, exportable = false)
    private LocalDateTime issueDate;
    @Title(value = "备注")
    private String comment;

    @Override
    public String toString() {
        return "Clerk{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
