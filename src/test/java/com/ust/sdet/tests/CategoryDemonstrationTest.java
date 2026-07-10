package com.ust.sdet.tests;

import com.ust.sdet.report.ExtentTestListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Framework Hardening")
@Feature("Reporting Insights")
@Owner("SDET Trainer")
@ExtendWith(ExtentTestListener.class)
public class CategoryDemonstrationTest {

    @Test
    @Story("Product defects demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deliberately fails a plain assertion so Allure buckets it under Product defects")
    void demonstratesProductDefectCategory() {
        int actualOrderCount = 1;
        int expectedOrderCount = 2;

        assertEquals(expectedOrderCount, actualOrderCount, "Order count mismatch");
    }

    @Test
    @Story("Test defects demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deliberately throws an unhandled exception so Allure buckets it under Test defects broken")
    void demonstratesTestDefectCategory() {
        Object nullOrder = null;

        nullOrder.toString();
    }

    @Test
    @Story("Flaky demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deliberately fails with a message matching the flaky messageRegex")
    void demonstratesFlakyCategory() {
        boolean connectionStable = false;

        if (!connectionStable) {
            throw new AssertionError("Order save failed: connection reset while writing to database");
        }
    }
}