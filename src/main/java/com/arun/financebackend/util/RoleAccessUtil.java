package com.arun.financebackend.util;

import com.arun.financebackend.exception.AccessDeniedException;
import com.arun.financebackend.model.Role;

/**
 * Utility class for role-based access control.
 *
 * We use a simple X-USER-ROLE request header instead of Spring Security.
 * This keeps the code easy to understand and easy to explain in an interview.
 *
 * Roles:
 *  - ADMIN  -> full access
 *  - ANALYST -> read records + dashboard only
 *  - VIEWER  -> dashboard only
 */
public class RoleAccessUtil {

    // Private constructor to prevent instantiation (this is a utility class)
    private RoleAccessUtil() {
    }

    /**
     * Parses the role from the X-USER-ROLE header string.
     * Throws IllegalArgumentException if the role is missing or invalid.
     */
    public static Role parseRole(String roleHeader) {
        if (roleHeader == null || roleHeader.isBlank()) {
            throw new IllegalArgumentException("X-USER-ROLE header is required. Allowed values: ADMIN, ANALYST, VIEWER");
        }
        try {
            return Role.valueOf(roleHeader.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + roleHeader + ". Allowed values: ADMIN, ANALYST, VIEWER");
        }
    }

    /**
     * Checks that the caller has ADMIN role.
     * Use this for create / update / delete / user management endpoints.
     */
    public static void requireAdmin(String roleHeader) {
        Role role = parseRole(roleHeader);
        if (role != Role.ADMIN) {
            throw new AccessDeniedException("Access denied. This action requires ADMIN role.");
        }
    }

    /**
     * Checks that the caller is ADMIN or ANALYST.
     * Use this for reading financial records.
     */
    public static void requireAnalystOrAdmin(String roleHeader) {
        Role role = parseRole(roleHeader);
        if (role != Role.ADMIN && role != Role.ANALYST) {
            throw new AccessDeniedException("Access denied. This action requires ANALYST or ADMIN role.");
        }
    }

    /**
     * Checks that the caller has any valid role (ADMIN, ANALYST, or VIEWER).
     * Use this for the dashboard summary endpoint.
     */
    public static void requireAnyRole(String roleHeader) {
        // Just parsing is enough — it will throw if the role is invalid
        parseRole(roleHeader);
    }
}
