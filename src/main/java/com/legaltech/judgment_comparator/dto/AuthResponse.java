package com.legaltech.judgment_comparator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for authentication (login/register)
 * Contains JWT token and user info
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;

    @Builder.Default // Fix: Add this annotation for default value
    private String type = "Bearer";

    private Long userId;
    private String username;
    private String email;
    private String role;
}
