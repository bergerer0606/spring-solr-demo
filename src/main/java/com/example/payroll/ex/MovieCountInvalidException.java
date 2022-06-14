package com.example.payroll.ex;

public class MovieCountInvalidException extends RuntimeException {

    private final Integer count;
    public MovieCountInvalidException(Integer count) {
        super("Count must be a value between between 0 and 100. It currently is " + count);
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
