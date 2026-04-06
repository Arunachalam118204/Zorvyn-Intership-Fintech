package com.arun.financebackend.dto;

import com.arun.financebackend.model.FinanceRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardSummaryResponse {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;
    private Map<String, BigDecimal> categoryTotals;
    private List<FinanceRecord> recentTransactions;

    // ---- Constructors ----

    public DashboardSummaryResponse() {
    }

    public DashboardSummaryResponse(BigDecimal totalIncome, BigDecimal totalExpense,
                                     BigDecimal netBalance,
                                     Map<String, BigDecimal> categoryTotals,
                                     List<FinanceRecord> recentTransactions) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netBalance = netBalance;
        this.categoryTotals = categoryTotals;
        this.recentTransactions = recentTransactions;
    }

    // ---- Getters and Setters ----

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public BigDecimal getNetBalance() {
        return netBalance;
    }

    public void setNetBalance(BigDecimal netBalance) {
        this.netBalance = netBalance;
    }

    public Map<String, BigDecimal> getCategoryTotals() {
        return categoryTotals;
    }

    public void setCategoryTotals(Map<String, BigDecimal> categoryTotals) {
        this.categoryTotals = categoryTotals;
    }

    public List<FinanceRecord> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<FinanceRecord> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
}
