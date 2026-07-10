package com.ust.sdet.tests;


import com.ust.sdet.report.ExtentTestListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Framework Hardening")
@Feature("Reporting Insights")
@Owner("SDET Trainer")
@ExtendWith(ExtentTestListener.class)
public class AllureReportInsightTest {

    private String categories;

    @BeforeEach
    void setup() throws IOException {
        categories = Files.readString(
                Path.of("src/test/resources/allure/categories.json"));

    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify categories.json file exists")
    void categoriesFileExists() {

        assertTrue(Files.exists(
                Path.of("src/test/resources/allure/categories.json")));
    }

    @Test
    @Story("Categories")
    @Description("Verify categories file is not empty")
    void categoriesFileIsNotEmpty() {

        assertFalse(categories.isBlank());
    }

    @Test
    @Story("Categories")
    @Description("Verify Flaky tests category exists")
    void flakyCategoryExists() {

        assertTrue(categories.contains("\"Flaky tests\""));
    }

    @Test
    @Story("Categories")
    @Description("Verify Test defects category exists")
    void testDefectCategoryExists() {

        assertTrue(categories.contains("\"Test defects {broken}\""));
    }

    @Test
    @Story("Categories")
    @Description("Verify Product defects category exists")
    void productDefectCategoryExists() {

        assertTrue(categories.contains("\"Product defects\""));
    }

    @Test
    @Story("Categories")
    @Description("Verify Flaky category comes before generic categories")
    void categoriesSpecificFlakyRuleBeforeGenericBuckets() {

        int flakyIndex = categories.indexOf("\"Flaky tests\"");
        int testDefectIndex = categories.indexOf("\"Test defects {broken}\"");
        int productDefectIndex = categories.indexOf("\"Product defects\"");

        assertTrue(flakyIndex >= 0);
        assertTrue(testDefectIndex > flakyIndex);
        assertTrue(productDefectIndex > flakyIndex);
    }

    @Test
    @Story("Categories")
    @Description("Verify flaky flag exists")
    void verifyFlakyFlag() {

        assertTrue(categories.contains("\"flaky\": true"));
    }

    @Test
    @Story("Categories")
    @Description("Verify flaky category matches failed and broken status")
    void verifyFlakyMatchedStatuses() {

        assertTrue(
                categories.contains("\"matchedStatuses\": [\"failed\",\"broken\"]"));
    }

    @Test
    @Story("Categories")
    @Description("Verify broken category matches broken status")
    void verifyBrokenMatchedStatus() {

        assertTrue(
                categories.contains("\"matchedStatuses\": [\"broken\"]"));
    }

    @Test
    @Story("Categories")
    @Description("Verify product category matches failed status")
    void verifyProductMatchedStatus() {

        assertTrue(
                categories.contains("\"matchedStatuses\": [\"failed\"]"));
    }

    @Test
    @Story("Categories")
    @Description("Verify message regex exists")
    void verifyMessageRegexExists() {

        assertTrue(categories.contains("\"messageRegex\""));
    }

    @Test
    @Story("Categories")
    @Description("Verify timeout keyword exists")
    void verifyTimeoutRegex() {

        assertTrue(categories.contains("timeout"));
    }

    @Test
    @Story("Categories")
    @Description("Verify stale element keyword exists")
    void verifyStaleElementRegex() {

        assertTrue(categories.contains("stale element"));
    }

    @Test
    @Story("Categories")
    @Description("Verify connection reset keyword exists")
    void verifyConnectionResetRegex() {

        assertTrue(categories.contains("connection reset"));
    }

    @Test
    @Story("Categories")
    @Description("Verify categories count")
    void verifyCategoryCount() {

        int count = categories.split("\"name\"").length - 1;

        assertEquals(3, count);
    }

    @Test
    @Story("Categories")
    @Description("Verify matchedStatuses count")
    void verifyMatchedStatusesCount() {

        int count = categories.split("\"matchedStatuses\"").length - 1;

        assertEquals(3, count);
    }

    @Test
    @Story("Categories")
    @Description("Verify failed status appears twice")
    void verifyFailedStatusCount() {

        int count = categories.split("\"failed\"").length - 1;

        assertEquals(2, count);
    }

    @Test
    @Story("Categories")
    @Description("Verify broken status appears twice")
    void verifyBrokenStatusCount() {

        int count = categories.split("\"broken\"").length - 1;

        assertEquals(2, count);
    }

}
