package com.example.payroll.ex;

public class OrderNotFoundException extends RuntimeException{
    private String id;

    public OrderNotFoundException(String id){
        super("Order not found with id " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
