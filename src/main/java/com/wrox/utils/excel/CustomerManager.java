package com.wrox.utils.excel;

import com.wrox.utils.excel.annotation.ExcelColumn;

/**
 * Created by dengb on 2015/10/24.
 */
public strictfp class CustomerManager {

    @ExcelColumn("经理号")
    private String managerId;
    @ExcelColumn("名称")
    private String name;
    @ExcelColumn("贷款FTP利润")
    private double loanFTPProfit;
    @ExcelColumn("存款FTP利润")
    private double depositFTPProfit;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLoanFTPProfit() {
        return loanFTPProfit;
    }

    public void setLoanFTPProfit(double loanFTPProfit) {
        this.loanFTPProfit = loanFTPProfit;
    }

    public double getDepositFTPProfit() {
        return depositFTPProfit;
    }

    public void setDepositFTPProfit(double depositFTPProfit) {
        this.depositFTPProfit = depositFTPProfit;
    }

    @Override
    public String toString() {
        return "CustomerManager{" +
                "depositFTPProfit=" + depositFTPProfit +
                ", loanFTPProfit=" + loanFTPProfit +
                ", name='" + name + '\'' +
                ", managerId='" + managerId + '\'' +
                '}';
    }
}
