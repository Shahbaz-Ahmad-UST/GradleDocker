package com.ust.sdet.model;

import java.time.LocalDate;

public record Order(
        Long id,
        String sku,
        int qty,
        double price,
        LocalDate orderDate,
        boolean shipped
) {
}