package com.viheakode.jwt.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiResponseStructure {
    public static ResponseEntity<Object> singleResp(String msg, Object data, HttpStatus httpStatus){
        Map<String, Object> objectMap = new LinkedHashMap<>();
        objectMap.put("timestamp", LocalDateTime.now());
        objectMap.put("status", httpStatus.value());
        objectMap.put("message", msg);
        objectMap.put("data", data);
        return new ResponseEntity<>(objectMap, httpStatus);
    }

    public static ResponseEntity<Object> respError(String error, String msg, String path, HttpStatus httpStatus){
        Map<String, Object> objectMap = new LinkedHashMap<>();
        objectMap.put("timestamp", LocalDateTime.now());
        objectMap.put("status", httpStatus.value());
        objectMap.put("error", error);
        objectMap.put("message", msg);
        objectMap.put("path", path);
        return new ResponseEntity<>(objectMap, httpStatus);
    }
}
