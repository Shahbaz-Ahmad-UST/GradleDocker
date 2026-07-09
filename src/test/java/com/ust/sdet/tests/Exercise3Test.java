package com.ust.sdet.tests;

import org.junit.jupiter.api.Test;

import static com.ust.sdet.builder.OrderBuilder.anOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Exercise3Test extends AbstractOrderTest {

    @Test
    void testA_createsOwnData() {
        factory.persisted(anOrder().withSku("SKU-A").build());

        assertEquals(1, repository.count());
    }

    @Test
    void testB_createsOwnData() {
        factory.persisted(anOrder().withSku("SKU-B").build());
        factory.persisted(anOrder().withSku("SKU-C").build());

        assertEquals(2, repository.count());
    }

    @Test
    void testC_createsOwnData() throws Exception {
        factory.persisted(anOrder().withSku("SKU-D").build());

        assertTrue(dbSupport.orderExists("SKU-D"));
    }
}