package com.viheakode.jwt.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UserModifyRequest {
    private List<String> roles;
}
