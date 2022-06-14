package com.example.payroll.ex;

public class MoviePageInvalidException extends RuntimeException {
    public MoviePageInvalidException(int page) {
        super("Page must be greater than 0. It currently is " + page);

    }
}
