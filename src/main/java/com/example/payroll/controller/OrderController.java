package com.example.payroll.controller;

import com.example.payroll.entity.Order;
import com.example.payroll.entity.OrderStatus;
import com.example.payroll.ex.OrderNotFoundException;
import com.example.payroll.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {
    private final OrderRepository repository;

    OrderController(OrderRepository repository){
        this.repository = repository;
    }

    @GetMapping("/orders")
    List<Order> all(){
        List<Order> orders = new ArrayList<>();

        repository.findAll().forEach(orders::add);

        return orders;
    }

    @GetMapping("/orders/{id}")
    Order one(@PathVariable String id){
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        return order;
    }

    @DeleteMapping("/orders/{id}/cancel")
    Order cancel(@PathVariable String id){
        Order cancelledOrder = repository.findById(id).map(order -> {
            order.setStatus(OrderStatus.CANCELLED);
            repository.save(order);
            return order;
        }).orElseThrow(() -> new OrderNotFoundException(id));

        return cancelledOrder;
    }

    @PutMapping("/orders/{id}/complete")
    Order complete(@PathVariable String id){
        return repository.findById(id).map(order -> {
            order.setStatus(OrderStatus.COMPLETED);
            repository.save(order);
            return order;
        }).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @GetMapping("/orders/find")
    List<Order> search(@RequestParam String term){
        return repository.findByDescriptionContaining(term);
    }

}
