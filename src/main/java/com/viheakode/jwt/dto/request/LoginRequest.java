package com.viheakode.jwt.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username field is required")
    private String username;
    @NotBlank(message = "Password field is required")
    private String password;
}
