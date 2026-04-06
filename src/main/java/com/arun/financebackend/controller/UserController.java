package com.arun.financebackend.controller;

import com.arun.financebackend.dto.UserCreateRequest;
import com.arun.financebackend.dto.UserRoleUpdateRequest;
import com.arun.financebackend.dto.UserStatusUpdateRequest;
import com.arun.financebackend.model.AppUser;
import com.arun.financebackend.service.UserService;
import com.arun.financebackend.util.RoleAccessUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController — handles all user management endpoints.
 * All endpoints are ADMIN only.
 * Role is passed via the X-USER-ROLE request header.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/users — Create a new user (ADMIN only)
    @PostMapping
    public ResponseEntity<AppUser> createUser(
            @RequestHeader("X-USER-ROLE") String roleHeader,
            @Valid @RequestBody UserCreateRequest request) {

        RoleAccessUtil.requireAdmin(roleHeader);
        AppUser created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/users — Get all users (ADMIN only)
    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers(
            @RequestHeader("X-USER-ROLE") String roleHeader) {

        RoleAccessUtil.requireAdmin(roleHeader);
        List<AppUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET /api/users/{id} — Get user by ID (ADMIN only)
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(
            @RequestHeader("X-USER-ROLE") String roleHeader,
            @PathVariable Long id) {

        RoleAccessUtil.requireAdmin(roleHeader);
        AppUser user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // PUT /api/users/{id}/role — Update a user's role (ADMIN only)
    @PutMapping("/{id}/role")
    public ResponseEntity<AppUser> updateUserRole(
            @RequestHeader("X-USER-ROLE") String roleHeader,
            @PathVariable Long id,
            @Valid @RequestBody UserRoleUpdateRequest request) {

        RoleAccessUtil.requireAdmin(roleHeader);
        AppUser updated = userService.updateUserRole(id, request);
        return ResponseEntity.ok(updated);
    }

    // PUT /api/users/{id}/status — Activate or deactivate a user (ADMIN only)
    @PutMapping("/{id}/status")
    public ResponseEntity<AppUser> updateUserStatus(
            @RequestHeader("X-USER-ROLE") String roleHeader,
            @PathVariable Long id,
            @Valid @RequestBody UserStatusUpdateRequest request) {

        RoleAccessUtil.requireAdmin(roleHeader);
        AppUser updated = userService.updateUserStatus(id, request);
        return ResponseEntity.ok(updated);
    }
}
