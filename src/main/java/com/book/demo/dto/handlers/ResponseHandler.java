package com.book.demo.dto.handlers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(
        HttpStatus httpStatus, 
        boolean isSuccess, 
        String message
    ) {
        Map<String, Object> responseResult = new LinkedHashMap<String, Object>();
        responseResult.put("statusCode", httpStatus.value());
        responseResult.put("success", isSuccess);
        responseResult.put("message", message);

        return new ResponseEntity<Object>(responseResult, httpStatus);
    }

    public static ResponseEntity<Object> generateResponse(
        HttpStatus httpStatus,
        boolean isSuccess, 
        String message, 
        Object data
    ) {
        Map<String, Object> responseResult = new LinkedHashMap<String, Object>();
        responseResult.put("statusCode", httpStatus.value());
        responseResult.put("success", isSuccess);
        responseResult.put("message", message);
        responseResult.put("data", data);

        return new ResponseEntity<Object>(responseResult, httpStatus);
    }

    public static ResponseEntity<Object> generateResponse(
        HttpStatus httpStatus,
        boolean isSuccess, 
        String message, 
        String token
    ) {
        Map<String, Object> responseResult = new LinkedHashMap<String, Object>();
        responseResult.put("statusCode", httpStatus.value());
        responseResult.put("success", isSuccess);
        responseResult.put("message", message);
        responseResult.put("token", token);

        return new ResponseEntity<Object>(responseResult, httpStatus);
    }
}
