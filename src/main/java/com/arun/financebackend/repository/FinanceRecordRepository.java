package com.arun.financebackend.repository;

import com.arun.financebackend.model.FinanceRecord;
import com.arun.financebackend.model.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinanceRecordRepository extends JpaRepository<FinanceRecord, Long> {

    // Filter by type
    List<FinanceRecord> findByType(RecordType type);

    // Filter by category (case-insensitive)
    List<FinanceRecord> findByCategoryIgnoreCase(String category);

    // Filter by date range
    List<FinanceRecord> findByDateBetween(LocalDate from, LocalDate to);

    // Filter by type and date range
    List<FinanceRecord> findByTypeAndDateBetween(RecordType type, LocalDate from, LocalDate to);

    // Filter by category and date range
    List<FinanceRecord> findByCategoryIgnoreCaseAndDateBetween(String category, LocalDate from, LocalDate to);

    // Get top 5 most recent transactions
    List<FinanceRecord> findTop5ByOrderByDateDesc();

    // Get all records by type (used in dashboard calculation)
    List<FinanceRecord> findAllByType(RecordType type);
}
