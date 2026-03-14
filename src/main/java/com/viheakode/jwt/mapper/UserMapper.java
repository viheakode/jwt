package com.viheakode.jwt.mapper;

import com.viheakode.jwt.dto.response.UserDto;
import com.viheakode.jwt.model.User;

public class UserMapper {
    public static UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUuid(user.getUuid());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
//        userDto.setPassword(userDto.getPassword());
        userDto.setStatus(user.getStatus());
        userDto.setPublisher(user.getPublisher());
        userDto.setPublishedDate(user.getPublishedDate());
        userDto.setModifiedDate(user.getModifiedDate());
        return userDto;
    }
}
