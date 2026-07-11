package com.ust.sdet.tests;

import com.ust.sdet.report.ExtentTestListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Demonstrates how each Allure status (Passed, Failed, Broken, Skipped, Unknown)
 * gets produced, with logging, step-level tracing, and attached evidence for each case.
 *
 * Unknown status is not produced here in code — see the injectUnknownAllureResult
 * Gradle task, since Unknown reflects an incomplete/crashed result, not a real assertion outcome.
 */
@Epic("Framework Hardening")
@Feature("Reporting Insights")
@Owner("SDET Trainer")
@ExtendWith(ExtentTestListener.class)
public class CategoryDemonstrationTest {

    private static final Logger log = LoggerFactory.getLogger(CategoryDemonstrationTest.class);

    @Test
    @Story("Passed demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("A normal passing test so Allure shows a Passed status")
    void demonstratesPassedCategory() {
        log.info("Starting demonstratesPassedCategory");

        int actualOrderCount = countOrders();
        int expectedOrderCount = 1;

        attachEvidence("passed-demo-data.txt",
                "actual=" + actualOrderCount + ", expected=" + expectedOrderCount);

        assertEquals(expectedOrderCount, actualOrderCount, "Order count should match");

        log.info("Finished demonstratesPassedCategory: PASSED");
    }

    @Test
    @Story("Product defects demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deliberately fails a plain assertion so Allure buckets it under Product defects")
    void demonstratesProductDefectCategory() {
        log.info("Starting demonstratesProductDefectCategory");

        int actualOrderCount = countOrders();
        int expectedOrderCount = 2;

        attachEvidence("product-defect-data.txt",
                "actual=" + actualOrderCount + ", expected=" + expectedOrderCount
                        + " -> mismatch is intentional for this demo");

        assertEquals(expectedOrderCount, actualOrderCount, "Order count mismatch");
    }

    @Test
    @Story("Test defects demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deliberately throws an unhandled exception so Allure buckets it under Test defects broken")
    void demonstratesTestDefectCategory() {
        log.info("Starting demonstratesTestDefectCategory");
        log.warn("About to dereference a null order reference on purpose");

        attachEvidence("test-defect-context.txt",
                "Simulating an unexpected NPE, e.g. a missing null-check before repository access");

        Object nullOrder = triggerNullOrderLookup();

        nullOrder.toString();
    }

    @Test
    @Story("Flaky demo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deliberately fails with a message matching the flaky messageRegex")
    void demonstratesFlakyCategory() {
        log.info("Starting demonstratesFlakyCategory");

        boolean connectionStable = simulateUnstableConnection();

        attachEvidence("flaky-demo-connection-state.txt",
                "connectionStable=" + connectionStable);

        if (!connectionStable) {
            log.error("Simulated connection instability detected");
            throw new AssertionError("Order save failed: connection reset while writing to database");
        }
    }

    @Test
    @Story("Skipped demo")
    @Severity(SeverityLevel.MINOR)
    @Description("Skipped at runtime via a failed assumption, not a hard failure")
    void demonstratesSkippedViaAssumption() {
        log.info("Starting demonstratesSkippedViaAssumption");

        Assumptions.assumeTrue(false, "Skipping because this environment doesn't have the required setup");

        assertEquals(1, 1);
    }

    @Test
    @Disabled("Demonstrates Allure's Skipped status for statically disabled tests")
    @Story("Skipped demo")
    @Severity(SeverityLevel.MINOR)
    @Description("Skipped before execution via the Disabled annotation")
    void demonstratesSkippedViaDisabled() {
        assertEquals(1, 1);
    }

    @Step("Count orders currently visible to the test")
    private int countOrders() {
        log.debug("countOrders() called");
        return 1;
    }

    @Step("Simulate a null order lookup")
    private Object triggerNullOrderLookup() {
        log.debug("triggerNullOrderLookup() called");
        return null;
    }

    @Step("Simulate an unstable database connection")
    private boolean simulateUnstableConnection() {
        log.debug("simulateUnstableConnection() called");
        return false;
    }

    @Attachment(value = "{name}", type = "text/plain")
    private byte[] attachEvidence(String name, String content) {
        log.info("Attaching evidence [{}]: {}", name, content);
        return content.getBytes(StandardCharsets.UTF_8);
    }
}