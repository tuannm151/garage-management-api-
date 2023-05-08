package com.example.GARA_API.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHander extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
//        List<String> validationListErr =
//                ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
//        return ExceptionUtils.buildResponseEntity(
//                ex.getMessage(),
//                validationListErr,
//                status,
//                (HttpServletRequest) request);
        ErrorResponse error = new ErrorResponse(status);
        error.setMessage("Validation failed");
        List<String> validationListErr =
                ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        error.setDetail(validationListErr);
        error.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
        return ResponseEntity.status(status).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
                List<String> validationListErr =
                ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ExceptionUtils.buildResponseEntity(
                "Validation failed",
                validationListErr,
                status,
                (HttpServletRequest) request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
                return ExceptionUtils.buildResponseEntity(
                "Wrong request format",
                null,
                HttpStatus.BAD_REQUEST,
                        (HttpServletRequest) request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                             HttpServletRequest request) {
        return ExceptionUtils.buildResponseEntity(
                ex.getMessage(),
                null,
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        return ExceptionUtils.buildResponseEntity(
                ex.getMessage(),
                null,
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(OperationNotSupportedException.class)
    public final ResponseEntity<Object> handleOperationNotSupportedException(OperationNotSupportedException ex,
                                                                             HttpServletRequest request) {
        return  ExceptionUtils.buildResponseEntity(
                ex.getMessage(),
                null,
                HttpStatus.FORBIDDEN,
                request);
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public final ResponseEntity<Object> handleCustomAuthenticationException(CustomAuthenticationException ex,
                                                                     HttpServletRequest request) {
        return ExceptionUtils.buildResponseEntity(
                ex.getMessage(),
                null,
                HttpStatus.UNAUTHORIZED,
                request);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex,
                                                                     HttpServletRequest request) {
        return ExceptionUtils.buildResponseEntity(
                ex.getMessage(),
                null,
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest request) {
        // ex.getMessage() for debug purpose, change it to "UNEXPECTED_ERROR" for production
        return ExceptionUtils.buildResponseEntity(
                ex.getMessage(),
                null,
                HttpStatus.BAD_REQUEST,
                request);
    }
}
