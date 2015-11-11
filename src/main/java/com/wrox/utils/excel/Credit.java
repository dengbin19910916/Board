package com.wrox.utils.excel;

import com.wrox.utils.excel.annotation.ExcelColumn;

import java.time.LocalDate;

/**
 * 信贷数据
 *
 * Created by dengb on 2015/10/26.
 */
public class Credit {

    //客户编号	客户名称	合同编号	借据编号	产品名称	担保方式	贷款科目	贷款科目名称	还款方式	合同金额(元)	贷款金额（元）


    @ExcelColumn("客户编号")
    private String clientId;
    @ExcelColumn("客户名称")
    private String clientName;
    @ExcelColumn("合同编号")
    private String contractNo;
    @ExcelColumn("借据编号")
    private String loans;
    @ExcelColumn("产品名称")
    private String productName;
    @ExcelColumn("担保方式")
    private String guarantyStyle;
    @ExcelColumn("贷款科目")
    private String loansSubjects;
    @ExcelColumn("贷款科目名称")
    private String loansSubjectsName;
    @ExcelColumn("还款方式")
    private String repaymentMethod;
    @ExcelColumn("合同金额(元)")
    private double contractAmount;
    @ExcelColumn("贷款金额（元）")
    private double loanAmount;
    @ExcelColumn("累计放款金额(元)")
    private double totalLoanAmount;
    @ExcelColumn("贷款余额(元)")
    private double loanBalance;
    @ExcelColumn("贷款期限(月)")
    private double loanPeriod;
    @ExcelColumn("贷款年利率（%）")
    private double  annualPercentageRate;
    @ExcelColumn(value = "发放日期", dateFormat = "yyyy/MM/dd")
    private LocalDate issueDate;
    @ExcelColumn(value = "到期日期", dateFormat = "yyyy/MM/dd")
    private LocalDate maturityDate;
    @ExcelColumn("应收本金(元)")
    private double receivables;
    @ExcelColumn("已收本金(元)")
    private double alreadyReceivedPrincipal;
    @ExcelColumn(value = "拖欠本金起始日期", dateFormat = "yyyy/MM/dd")
    private LocalDate principalStartDate;
    @ExcelColumn("应收利息(元)")
    private double interestReceivable;
    @ExcelColumn("已收利息(元)")
    private double interestPaid;
    @ExcelColumn(value = "拖欠利息起始日期", dateFormat = "yyyy/MM/dd")
    private LocalDate interestStartDate;
    @ExcelColumn("表内应收利息(元)")
    private double tableShouldReceiveInterest;
    @ExcelColumn("表外应收利息(元)")
    private double offBalanceSheetReceivables;
    @ExcelColumn("拖欠本金(元)")
    private double defaultPrincipal;
    @ExcelColumn("拖欠利息(元)")
    private double defaultInterest;
    @ExcelColumn("是否受托支付")
    private double whetherToPay;
    @ExcelColumn("受托支付金额(元)")
    private double trusteePaymentAmount;
    @ExcelColumn("自主支付金额(元)")
    private double selfPaidAmount;
    @ExcelColumn("已支付金额(元)")
    private double paidAmount;
    @ExcelColumn("未支付金额(元)")
    private double unpaidAmount;
    @ExcelColumn("风险状态")
    private String riskStat;
    @ExcelColumn("拖欠标识")
    private String defaultIdentification;
    @ExcelColumn("结清标识")
    private String clearanceIdentification;
    @ExcelColumn("支付方式")
    private String paymentMethod;
    @ExcelColumn("经办行")
    private String handlingBank;
    @ExcelColumn("经办人")
    private String master;
    @ExcelColumn("经办人主账号")
    private String masterAccount;
    @ExcelColumn("贷款账号")
    private String loanAccount;
    @ExcelColumn("产品代码")
    private String productCode;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getLoans() {
        return loans;
    }

    public void setLoans(String loans) {
        this.loans = loans;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGuarantyStyle() {
        return guarantyStyle;
    }

    public void setGuarantyStyle(String guarantyStyle) {
        this.guarantyStyle = guarantyStyle;
    }

    public String getLoansSubjects() {
        return loansSubjects;
    }

    public void setLoansSubjects(String loansSubjects) {
        this.loansSubjects = loansSubjects;
    }

    public String getLoansSubjectsName() {
        return loansSubjectsName;
    }

    public void setLoansSubjectsName(String loansSubjectsName) {
        this.loansSubjectsName = loansSubjectsName;
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(double totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public double getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(double loanBalance) {
        this.loanBalance = loanBalance;
    }

    public double getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(double loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public double getAnnualPercentageRate() {
        return annualPercentageRate;
    }

    public void setAnnualPercentageRate(double annualPercentageRate) {
        this.annualPercentageRate = annualPercentageRate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public double getReceivables() {
        return receivables;
    }

    public void setReceivables(double receivables) {
        this.receivables = receivables;
    }

    public double getAlreadyReceivedPrincipal() {
        return alreadyReceivedPrincipal;
    }

    public void setAlreadyReceivedPrincipal(double alreadyReceivedPrincipal) {
        this.alreadyReceivedPrincipal = alreadyReceivedPrincipal;
    }

    public LocalDate getPrincipalStartDate() {
        return principalStartDate;
    }

    public void setPrincipalStartDate(LocalDate principalStartDate) {
        this.principalStartDate = principalStartDate;
    }

    public double getInterestReceivable() {
        return interestReceivable;
    }

    public void setInterestReceivable(double interestReceivable) {
        this.interestReceivable = interestReceivable;
    }

    public double getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public LocalDate getInterestStartDate() {
        return interestStartDate;
    }

    public void setInterestStartDate(LocalDate interestStartDate) {
        this.interestStartDate = interestStartDate;
    }

    public double getTableShouldReceiveInterest() {
        return tableShouldReceiveInterest;
    }

    public void setTableShouldReceiveInterest(double tableShouldReceiveInterest) {
        this.tableShouldReceiveInterest = tableShouldReceiveInterest;
    }

    public double getOffBalanceSheetReceivables() {
        return offBalanceSheetReceivables;
    }

    public void setOffBalanceSheetReceivables(double offBalanceSheetReceivables) {
        this.offBalanceSheetReceivables = offBalanceSheetReceivables;
    }

    public double getDefaultPrincipal() {
        return defaultPrincipal;
    }

    public void setDefaultPrincipal(double defaultPrincipal) {
        this.defaultPrincipal = defaultPrincipal;
    }

    public double getDefaultInterest() {
        return defaultInterest;
    }

    public void setDefaultInterest(double defaultInterest) {
        this.defaultInterest = defaultInterest;
    }

    public double getWhetherToPay() {
        return whetherToPay;
    }

    public void setWhetherToPay(double whetherToPay) {
        this.whetherToPay = whetherToPay;
    }

    public double getTrusteePaymentAmount() {
        return trusteePaymentAmount;
    }

    public void setTrusteePaymentAmount(double trusteePaymentAmount) {
        this.trusteePaymentAmount = trusteePaymentAmount;
    }

    public double getSelfPaidAmount() {
        return selfPaidAmount;
    }

    public void setSelfPaidAmount(double selfPaidAmount) {
        this.selfPaidAmount = selfPaidAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(double unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public String getRiskStat() {
        return riskStat;
    }

    public void setRiskStat(String riskStat) {
        this.riskStat = riskStat;
    }

    public String getDefaultIdentification() {
        return defaultIdentification;
    }

    public void setDefaultIdentification(String defaultIdentification) {
        this.defaultIdentification = defaultIdentification;
    }

    public String getClearanceIdentification() {
        return clearanceIdentification;
    }

    public void setClearanceIdentification(String clearanceIdentification) {
        this.clearanceIdentification = clearanceIdentification;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getHandlingBank() {
        return handlingBank;
    }

    public void setHandlingBank(String handlingBank) {
        this.handlingBank = handlingBank;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getMasterAccount() {
        return masterAccount;
    }

    public void setMasterAccount(String masterAccount) {
        this.masterAccount = masterAccount;
    }

    public String getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", loans='" + loans + '\'' +
                ", productName='" + productName + '\'' +
                ", guarantyStyle='" + guarantyStyle + '\'' +
                ", loansSubjects='" + loansSubjects + '\'' +
                ", loansSubjectsName='" + loansSubjectsName + '\'' +
                ", repaymentMethod='" + repaymentMethod + '\'' +
                ", contractAmount=" + contractAmount +
                ", loanAmount=" + loanAmount +
                ", totalLoanAmount=" + totalLoanAmount +
                ", loanBalance=" + loanBalance +
                ", loanPeriod=" + loanPeriod +
                ", annualPercentageRate=" + annualPercentageRate +
                ", issueDate=" + issueDate +
                ", maturityDate=" + maturityDate +
                ", receivables=" + receivables +
                ", alreadyReceivedPrincipal=" + alreadyReceivedPrincipal +
                ", principalStartDate=" + principalStartDate +
                ", interestReceivable=" + interestReceivable +
                ", interestPaid=" + interestPaid +
                ", interestStartDate=" + interestStartDate +
                ", tableShouldReceiveInterest=" + tableShouldReceiveInterest +
                ", offBalanceSheetReceivables=" + offBalanceSheetReceivables +
                ", defaultPrincipal=" + defaultPrincipal +
                ", defaultInterest=" + defaultInterest +
                ", whetherToPay=" + whetherToPay +
                ", trusteePaymentAmount=" + trusteePaymentAmount +
                ", selfPaidAmount=" + selfPaidAmount +
                ", paidAmount=" + paidAmount +
                ", unpaidAmount=" + unpaidAmount +
                ", riskStat='" + riskStat + '\'' +
                ", defaultIdentification='" + defaultIdentification + '\'' +
                ", clearanceIdentification='" + clearanceIdentification + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", handlingBank='" + handlingBank + '\'' +
                ", master='" + master + '\'' +
                ", masterAccount='" + masterAccount + '\'' +
                ", loanAccount='" + loanAccount + '\'' +
                ", productCode='" + productCode + '\'' +
                '}';
    }

    // 累计放款金额(元)	贷款余额(元)	贷款期限(月)	贷款年利率（%）	发放日期	到期日期	应收本金(元)	已收本金(元)	拖欠本金起始日期
    // 应收利息(元)	已收利息(元)	利息收入(元)	拖欠利息起始日期	表内应收利息(元)	表外应收利息(元)	拖欠本金(元)	拖欠利息(元)
    // 是否受托支付	受托支付金额(元)	自主支付金额(元)	已支付金额(元)	未支付金额(元)	风险状态	拖欠标识	结清标识	支付方式
    // 经办行	经办人	经办人主账号	贷款账号	产品代码
}
