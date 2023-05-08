package com.example.GARA_API.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ExceptionUtils {
    public static void raiseErrorJson(String message, List<String> details, HttpStatus status,
                                      HttpServletRequest request
                                                        ,HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        ErrorResponse error = new ErrorResponse(status);
        error.setMessage(message);
        error.setPath(request.getRequestURI());
        error.setDetail(details);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
    public static ResponseEntity<Object> buildResponseEntity(String message, List<String> details, HttpStatus status,
                                           HttpServletRequest request
            ) {
        ErrorResponse error = new ErrorResponse(status);
        error.setMessage(message);
        error.setPath(request.getRequestURI());
        error.setDetail(details);
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(error);
    }

}
