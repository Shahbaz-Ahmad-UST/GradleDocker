package com.ust.sdet.tests;

import com.ust.sdet.model.Order;
import org.junit.jupiter.api.Test;

import static com.ust.sdet.builder.OrderBuilder.anOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Exercise1Test extends AbstractOrderTest {

    @Test
    void createsOrderWithBuilderDefaults() {
        Order order = factory.persisted(anOrder().build());

        assertNotNull(order.id());
        assertEquals("SKU-1", order.sku());
        assertEquals(1, order.qty());
    }

    @Test
    void overridesOnlyQty() {
        Order order = factory.persisted(anOrder().withQty(3).build());

        assertEquals(3, order.qty());
        assertEquals("SKU-1", order.sku());
    }

    @Test
    void overridesOnlySku() {
        Order order = factory.persisted(anOrder().withSku("SKU-99").build());

        assertEquals("SKU-99", order.sku());
        assertEquals(1, order.qty());
    }
}