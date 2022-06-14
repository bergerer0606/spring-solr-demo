package com.example.payroll.controller;

import com.example.payroll.ex.MovieCountInvalidException;
import com.example.payroll.ex.MoviePageInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MovieControllerAdvice {

    @ResponseBody
    @ExceptionHandler(MovieCountInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String movieCountInvalidHandler(MovieCountInvalidException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MoviePageInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String moviePageInvalidHandler(MoviePageInvalidException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String missingRequestParameterHandler(MissingServletRequestParameterException e){
        return e.getMessage();
    }
}
