package com.arun.financebackend.controller;

import com.arun.financebackend.dto.FinanceRecordRequest;
import com.arun.financebackend.model.FinanceRecord;
import com.arun.financebackend.service.FinanceRecordService;
import com.arun.financebackend.util.RoleAccessUtil;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * FinanceRecordController — handles CRUD and filter operations on financial records.
 *
 * Access rules:
 *  POST, PUT, DELETE -> ADMIN only
 *  GET, filter       -> ADMIN or ANALYST
 */
@RestController
@RequestMapping("/api/records")
public class FinanceRecordController {

    private final FinanceRecordService financeRecordService;

    public FinanceRecordController(FinanceRecordService financeRecordService) {
        this.financeRecordService = financeRecordService;
    }

    // POST /api/records — Create a record (ADMIN only)
    @PostMapping
    public ResponseEntity<FinanceRecord> createRecord(
            @RequestHeader("X-USER-ROLE") String roleHeader,
            @Valid @RequestBody FinanceRecordRequest request) {

        RoleAccessUtil.requireAdmin(roleHeader);
        FinanceRecord created = financeRecordService.createRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/records — Get all records (ADMIN, ANALYST)
    @GetMapping
    public ResponseEntity<List<FinanceRecord>> getAllRecords(
            @RequestHeader("X-USER-ROLE") String roleHeader) {

        RoleAccessUtil.requireAnalystOrAdmin(roleHeader);
        List<FinanceRecord> records = financeRecordService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    // GET /api/records/{id} — Get record by ID (ADMIN, ANALYST)
    @GetMapping("/{id}")
    public ResponseEntity<FinanceRecord> getRecordById(
            @RequestHeader("X-USER-ROLE") String roleHeader,
            @PathVariable Long id) {

        RoleAccessUtil.requireAnalystOrAdmin(roleHeader);
        FinanceRecord record = financeRecordService.getRecordById(id);
        return ResponseEntity.ok(record);
    }

    // PUT /api/records/{id} — Update a record (ADMIN only)
    @PutMapping("/{id}")
    public ResponseEntity<FinanceRecord> updateRecord(
            @RequestHeader("X-USER-ROLE") String roleHeader,
            @PathVariable Long id,
            @Valid @RequestBody FinanceRecordRequest request) {

        RoleAccessUtil.requireAdmin(roleHeader);
        FinanceRecord updated = financeRecordService.updateRecord(id, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/records/{id} — Delete a record (ADMIN only)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(
            @RequestHeader("X-USER-ROLE") String roleHeader,
            @PathVariable Long id) {

        RoleAccessUtil.requireAdmin(roleHeader);
        financeRecordService.deleteRecord(id);
        return ResponseEntity.ok("Record with id " + id + " deleted successfully.");
    }

    // GET /api/records/filter — Filter records (ADMIN, ANALYST)
    // Query params: type, category, from (yyyy-MM-dd), to (yyyy-MM-dd)
    @GetMapping("/filter")
    public ResponseEntity<List<FinanceRecord>> filterRecords(
            @RequestHeader("X-USER-ROLE") String roleHeader,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        RoleAccessUtil.requireAnalystOrAdmin(roleHeader);
        List<FinanceRecord> filtered = financeRecordService.filterRecords(type, category, from, to);
        return ResponseEntity.ok(filtered);
    }
}
