package com.arun.financebackend.dto;

import com.arun.financebackend.model.Role;
import jakarta.validation.constraints.NotNull;

public class UserRoleUpdateRequest {

    @NotNull(message = "Role is required")
    private Role role;

    // ---- Getters and Setters ----

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
