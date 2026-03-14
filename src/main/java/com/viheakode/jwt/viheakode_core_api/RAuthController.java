package com.viheakode.jwt.viheakode_core_api;

import com.viheakode.jwt.dto.request.LoginRequest;
import com.viheakode.jwt.dto.request.RegisterRequest;
import com.viheakode.jwt.dto.response.UserDto;
import com.viheakode.jwt.service.serviceImp.AuthServiceImp;
import com.viheakode.jwt.util.ApiResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RAuthController {

    private final AuthServiceImp authServiceImp;

    public RAuthController(AuthServiceImp authServiceImp) {
        this.authServiceImp = authServiceImp;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest){
        String token = authServiceImp.authenticate(loginRequest);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("token", token);
        return ResponseEntity.ok().body(objectMap);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest registerRequest){
        UserDto userDto = authServiceImp.register(registerRequest);
        return ApiResponseStructure.singleResp("Register Successfully", userDto, HttpStatus.CREATED);
    }
}
