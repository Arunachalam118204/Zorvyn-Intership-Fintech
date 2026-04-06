package com.arun.financebackend.dto;

import com.arun.financebackend.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserCreateRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Role is required")
    private Role role;

    // ---- Getters and Setters ----

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
