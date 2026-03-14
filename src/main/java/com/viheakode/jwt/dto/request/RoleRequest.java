package com.viheakode.jwt.dto.request;

import lombok.Data;

@Data
public class RoleRequest {
    private String roleName;
    private String description;
}
