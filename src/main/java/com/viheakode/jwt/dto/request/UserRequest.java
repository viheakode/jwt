package com.viheakode.jwt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Username field is required")
    private String username;

    @NotBlank(message = "Email field is required")
    private String email;

    @NotBlank(message = "Password field is required")
    @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters")
    private String password;
}
