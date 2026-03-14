package com.viheakode.jwt.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class RoleDto {
    private Long roleId;
    private String uuid;
    @NotBlank(message = "RoleName field is required")
    private String roleName;
    private String description;
    private String status;
    private String publisher;
    private Date publishedDate;
    private Date modifiedDate;
}
