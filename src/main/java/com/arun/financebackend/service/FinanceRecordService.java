package com.arun.financebackend.service;

import com.arun.financebackend.dto.FinanceRecordRequest;
import com.arun.financebackend.exception.ResourceNotFoundException;
import com.arun.financebackend.model.FinanceRecord;
import com.arun.financebackend.model.RecordType;
import com.arun.financebackend.repository.FinanceRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinanceRecordService {

    private final FinanceRecordRepository financeRecordRepository;

    public FinanceRecordService(FinanceRecordRepository financeRecordRepository) {
        this.financeRecordRepository = financeRecordRepository;
    }

    // Create a new financial record
    public FinanceRecord createRecord(FinanceRecordRequest request) {
        FinanceRecord record = new FinanceRecord();
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());
        return financeRecordRepository.save(record);
    }

    // Get all financial records
    public List<FinanceRecord> getAllRecords() {
        return financeRecordRepository.findAll();
    }

    // Get a single record by ID
    public FinanceRecord getRecordById(Long id) {
        return financeRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Finance record not found with id: " + id));
    }

    // Update an existing record
    public FinanceRecord updateRecord(Long id, FinanceRecordRequest request) {
        FinanceRecord record = getRecordById(id);
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());
        return financeRecordRepository.save(record);
    }

    // Delete a record by ID
    public void deleteRecord(Long id) {
        FinanceRecord record = getRecordById(id); // throws 404 if not found
        financeRecordRepository.delete(record);
    }

    // Filter records with optional parameters
    public List<FinanceRecord> filterRecords(String type, String category, LocalDate from, LocalDate to) {

        RecordType recordType = null;

        // Parse type if provided
        if (type != null && !type.isBlank()) {
            try {
                recordType = RecordType.valueOf(type.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid type: " + type + ". Allowed values: INCOME, EXPENSE");
            }
        }

        // All four combinations of optional filters
        if (recordType != null && from != null && to != null) {
            return financeRecordRepository.findByTypeAndDateBetween(recordType, from, to);
        } else if (recordType != null) {
            return financeRecordRepository.findByType(recordType);
        } else if (category != null && !category.isBlank() && from != null && to != null) {
            return financeRecordRepository.findByCategoryIgnoreCaseAndDateBetween(category, from, to);
        } else if (category != null && !category.isBlank()) {
            return financeRecordRepository.findByCategoryIgnoreCase(category);
        } else if (from != null && to != null) {
            return financeRecordRepository.findByDateBetween(from, to);
        } else {
            // No filter — return all
            return financeRecordRepository.findAll();
        }
    }
}
