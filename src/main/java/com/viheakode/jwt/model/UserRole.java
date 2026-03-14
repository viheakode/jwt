package com.viheakode.jwt.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tblUserRole")
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;
    private Long userId;
    private Long roleId;
}
