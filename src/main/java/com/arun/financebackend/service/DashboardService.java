package com.arun.financebackend.service;

import com.arun.financebackend.dto.DashboardSummaryResponse;
import com.arun.financebackend.model.FinanceRecord;
import com.arun.financebackend.model.RecordType;
import com.arun.financebackend.repository.FinanceRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final FinanceRecordRepository financeRecordRepository;

    public DashboardService(FinanceRecordRepository financeRecordRepository) {
        this.financeRecordRepository = financeRecordRepository;
    }

    public DashboardSummaryResponse getSummary() {

        // Step 1: Get all income and expense records
        List<FinanceRecord> incomeRecords = financeRecordRepository.findAllByType(RecordType.INCOME);
        List<FinanceRecord> expenseRecords = financeRecordRepository.findAllByType(RecordType.EXPENSE);

        // Step 2: Calculate total income
        BigDecimal totalIncome = incomeRecords.stream()
                .map(FinanceRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Step 3: Calculate total expense
        BigDecimal totalExpense = expenseRecords.stream()
                .map(FinanceRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Step 4: Calculate net balance
        BigDecimal netBalance = totalIncome.subtract(totalExpense);

        // Step 5: Calculate category-wise totals (across all records)
        List<FinanceRecord> allRecords = financeRecordRepository.findAll();
        Map<String, BigDecimal> categoryTotals = new HashMap<>();

        for (FinanceRecord record : allRecords) {
            String category = record.getCategory();
            BigDecimal current = categoryTotals.getOrDefault(category, BigDecimal.ZERO);
            categoryTotals.put(category, current.add(record.getAmount()));
        }

        // Step 6: Get latest 5 transactions (sorted by date descending)
        List<FinanceRecord> recentTransactions = financeRecordRepository.findTop5ByOrderByDateDesc();

        // Step 7: Build and return the response
        return new DashboardSummaryResponse(
                totalIncome,
                totalExpense,
                netBalance,
                categoryTotals,
                recentTransactions
        );
    }
}
