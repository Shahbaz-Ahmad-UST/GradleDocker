package com.ust.sdet.tests;

import com.ust.sdet.report.ExtentTestListener;
import io.qameta.allure.*;
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

    private String categories() throws IOException {
        return Files.readString(Path.of("src/test/resources/allure/categories.json"));
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Categories split flaky, test and product failures so each bucket has an error")
    void categoriesSpecificFlakyRuleBeforeGenericBuckets() throws IOException {
        String categories = categories();

        int flakyIndex = categories.indexOf("\"Flaky tests\"");
        int testDefectIndex = categories.indexOf("\"Test defects {broken}\"");
        int productDefectIndex = categories.indexOf("\"Product defects\"");

        assertTrue(flakyIndex >= 0, "Flaky category must exist");
        assertTrue(testDefectIndex > flakyIndex, "Specific flaky rule must run before gen");
        assertTrue(productDefectIndex > flakyIndex, "Specific flaky rule must fun before");
        assertTrue(categories.contains("\"flaky\": true"));
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.NORMAL)
    @Description("Flaky category matches both failed and broken statuses")
    void flakyCategoryMatchesFailedAndBroken() throws IOException {
        String categories = categories();

        int flakyBlockStart = categories.indexOf("\"Flaky tests\"");
        int flakyBlockEnd = categories.indexOf("}", categories.indexOf("\"flaky\": true"));
        String flakyBlock = categories.substring(flakyBlockStart, flakyBlockEnd);

        assertTrue(flakyBlock.contains("\"failed\""), "Flaky category must match failed status");
        assertTrue(flakyBlock.contains("\"broken\""), "Flaky category must match broken status");
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.NORMAL)
    @Description("Flaky category has a messageRegex covering timeout, stale element, and connection reset")
    void flakyCategoryHasExpectedMessageRegex() throws IOException {
        String categories = categories();

        int flakyBlockStart = categories.indexOf("\"Flaky tests\"");
        int flakyBlockEnd = categories.indexOf("}", categories.indexOf("\"flaky\": true"));
        String flakyBlock = categories.substring(flakyBlockStart, flakyBlockEnd);

        assertTrue(flakyBlock.contains("\"messageRegex\""), "Flaky category must define a messageRegex");
        assertTrue(flakyBlock.contains("timeout"), "messageRegex must cover timeout");
        assertTrue(flakyBlock.contains("stale element"), "messageRegex must cover stale element");
        assertTrue(flakyBlock.contains("connection reset"), "messageRegex must cover connection reset");
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test defects category matches only broken status")
    void testDefectsCategoryMatchesOnlyBroken() throws IOException {
        String categories = categories();

        int blockStart = categories.indexOf("\"Test defects {broken}\"");
        int blockEnd = categories.indexOf("}", blockStart);
        String block = categories.substring(blockStart, blockEnd);

        assertTrue(block.contains("\"matchedStatuses\""), "Test defects category must define matchedStatuses");
        assertTrue(block.contains("\"broken\""), "Test defects category must match broken");
        assertFalse(block.contains("\"failed\""), "Test defects category must not also match failed");
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.NORMAL)
    @Description("Product defects category matches only failed status")
    void productDefectsCategoryMatchesOnlyFailed() throws IOException {
        String categories = categories();

        int blockStart = categories.indexOf("\"Product defects\"");
        int blockEnd = categories.indexOf("}", blockStart);
        String block = categories.substring(blockStart, blockEnd);

        assertTrue(block.contains("\"matchedStatuses\""), "Product defects category must define matchedStatuses");
        assertTrue(block.contains("\"failed\""), "Product defects category must match failed");
        assertFalse(block.contains("\"broken\""), "Product defects category must not also match broken");
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.MINOR)
    @Description("Categories file contains exactly the three expected named categories")
    void categoriesFileHasExactlyThreeCategories() throws IOException {
        String categories = categories();

        int nameCount = 0;
        int index = 0;
        while ((index = categories.indexOf("\"name\":", index)) != -1) {
            nameCount++;
            index += "\"name\":".length();
        }

        assertEquals(3, nameCount, "categories.json should define exactly three categories");
    }

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.MINOR)
    @Description("Categories file is a well-formed JSON array")
    void categoriesFileIsWellFormedArray() throws IOException {
        String categories = categories().trim();

        assertTrue(categories.startsWith("["), "categories.json must start with an array bracket");
        assertTrue(categories.endsWith("]"), "categories.json must end with an array bracket");

        long openBraces = categories.chars().filter(c -> c == '{').count();
        long closeBraces = categories.chars().filter(c -> c == '}').count();

        assertEquals(openBraces, closeBraces, "Every opening brace must have a matching closing brace");
    }
}