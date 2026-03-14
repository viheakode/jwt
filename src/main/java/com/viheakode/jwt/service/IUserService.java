package com.viheakode.jwt.service;

import com.viheakode.jwt.dto.request.ResetPasswordRequest;
import com.viheakode.jwt.dto.request.UserModifyRequest;
import com.viheakode.jwt.dto.request.UserRequest;
import com.viheakode.jwt.dto.response.UserDto;

import java.util.List;

public interface IUserService {
    UserDto addUser(UserRequest userRequest);
    List<UserDto> getUsers();
    UserDto getUser(Long userId);
    UserDto updateUser(Long userId, UserRequest userRequest);
    UserDto deleteUser(Long userId);
    UserDto modifyUser(Long userId, UserModifyRequest userModifyRequest);

    void resetPassword(Long userId, ResetPasswordRequest resetPasswordRequest);
}
