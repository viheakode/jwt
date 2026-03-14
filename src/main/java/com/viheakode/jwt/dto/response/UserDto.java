package com.viheakode.jwt.dto.response;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long userId;
    private String uuid;
    private String username;
    private String email;
//    private String password;
    private String status;
    private String publisher;
    private Date publishedDate;
    private Date modifiedDate;
}
