package com.ust.sdet.factory;

import com.ust.sdet.model.Order;
import com.ust.sdet.repository.OrderRepository;

public class OrderFactory {

    private final OrderRepository repo;

    public OrderFactory(OrderRepository repo) {
        this.repo = repo;
    }

    public Order persisted(Order order) {
        return repo.save(order);
    }
}