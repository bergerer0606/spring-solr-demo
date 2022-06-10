package com.example.payroll.ex;

public class EmployeeNotFoundException extends RuntimeException{

    public final String id;

    public EmployeeNotFoundException(String id){
        super("Cannot find employee with id " + id);

        this.id = id;
    }

    public String getId() {
        return id;
    }
}
