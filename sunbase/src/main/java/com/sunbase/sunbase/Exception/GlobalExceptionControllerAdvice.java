package com.sunbase.sunbase.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@PropertySource("classpath:ExceptionMessages.properties")
public class GlobalExceptionControllerAdvice {
    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionControllerAdvice.class);

    @Autowired
    Environment environment;

    @ExceptionHandler({CustomerNotFoundException.class,Exception.class})
    public ResponseEntity<ErrorInfo> handleExceptions(Exception e) {
        System.out.println(e.getMessage());
        logger.error(environment.getProperty(e.getMessage()));
        return new ResponseEntity<>( ErrorInfo.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage(environment.getProperty(e.getMessage()))
                .error(HttpStatus.BAD_REQUEST.toString())
                .build(),HttpStatus.BAD_REQUEST);

    }
}
