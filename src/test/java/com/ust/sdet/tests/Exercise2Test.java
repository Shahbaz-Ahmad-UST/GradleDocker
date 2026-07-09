package com.ust.sdet.tests;

import org.junit.jupiter.api.Test;

import static com.ust.sdet.builder.OrderBuilder.anOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Exercise2Test extends AbstractOrderTest {

    @Test
    void createOrderIndependently() {
        factory.persisted(anOrder().build());

        assertEquals(1, repository.count());
    }

    @Test
    void countOrdersIndependently() {
        factory.persisted(anOrder().build());
        factory.persisted(anOrder().withSku("SKU-2").build());

        assertEquals(2, repository.count());
    }
}