package com.viheakode.jwt.viheakode_core_api;

import com.viheakode.jwt.dto.request.ResetPasswordRequest;
import com.viheakode.jwt.dto.request.UserModifyRequest;
import com.viheakode.jwt.dto.request.UserRequest;
import com.viheakode.jwt.dto.response.UserDto;
import com.viheakode.jwt.service.serviceImp.UserServiceImp;
import com.viheakode.jwt.util.ApiResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class RUserController {

    private final UserServiceImp userServiceImp;
    public RUserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody UserRequest userRequest){
        UserDto userDto = userServiceImp.addUser(userRequest);
        return ApiResponseStructure.singleResp("Created", userDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        List<UserDto> userDtoList = userServiceImp.getUsers();
        return ApiResponseStructure.singleResp("Ok", userDtoList, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> get(@PathVariable Long userId){
        UserDto userDto = userServiceImp.getUser(userId);
        return ApiResponseStructure.singleResp("Ok", userDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable Long userId, @Valid @RequestBody UserRequest userRequest){
        UserDto userDto = userServiceImp.updateUser(userId, userRequest);
        return ApiResponseStructure.singleResp("Updated", userDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable Long userId){
        UserDto userDto = userServiceImp.deleteUser(userId);
        return ApiResponseStructure.singleResp("Deleted", userDto, HttpStatus.OK);
    }

    @PostMapping("/modify/{userId}")
    public ResponseEntity<Object> modify(@PathVariable Long userId, @RequestBody UserModifyRequest userModifyRequest){
        UserDto userDto = userServiceImp.modifyUser(userId, userModifyRequest);
        return ApiResponseStructure.singleResp("Created", userDto, HttpStatus.CREATED);
    }

    @PostMapping("/reset/{userId}")
    public ResponseEntity<Object> reset(@PathVariable Long userId, @Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        userServiceImp.resetPassword(userId, resetPasswordRequest);
        return ResponseEntity.ok().body("Reset successfully");
    }
}
