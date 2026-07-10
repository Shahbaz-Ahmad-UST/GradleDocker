package com.ust.sdet.tests;


import com.ust.sdet.report.ExtentTestListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @DisplayName("Verify categories.json file exists")
    void verifyCategoriesFileExists() {

        Path path = Path.of("src/test/resources/allure/categories.json");

        assertTrue(Files.exists(path));
    }

    @Test
    @DisplayName("Verify categories.json is not empty")
    void verifyFileNotEmpty() {

        assertFalse(categories.isBlank());
    }

    @Test
    @DisplayName("Verify JSON starts with array")
    void verifyStartsWithArray() {

        assertTrue(categories.trim().startsWith("["));
    }

    @Test
    @DisplayName("Verify JSON ends with array")
    void verifyEndsWithArray() {

        assertTrue(categories.trim().endsWith("]"));
    }

    @Test
    @DisplayName("Verify JSON is valid")
    void verifyValidJson() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.readTree(categories);

        assertTrue(node.isArray());
    }

    @Test
    @DisplayName("Verify Flaky category exists")
    void verifyFlakyCategoryExists() {

        assertTrue(categories.contains("\"Flaky tests\""));
    }

    @Test
    @DisplayName("Verify Broken category exists")
    void verifyBrokenCategoryExists() {

        assertTrue(categories.contains("\"Test defects {broken}\""));
    }

    @Test
    @DisplayName("Verify Product category exists")
    void verifyProductCategoryExists() {

        assertTrue(categories.contains("\"Product defects\""));
    }

    @Test
    @DisplayName("Verify exactly three categories")
    void verifyThreeCategories() {

        int count = categories.split("\"name\"").length - 1;

        assertEquals(3, count);
    }

    @Test
    @DisplayName("Verify Flaky category appears before generic categories")
    void verifyFlakyCategoryOrder() {

        int flakyIndex = categories.indexOf("\"Flaky tests\"");
        int brokenIndex = categories.indexOf("\"Test defects {broken}\"");
        int productIndex = categories.indexOf("\"Product defects\"");

        assertTrue(flakyIndex >= 0);
        assertTrue(brokenIndex > flakyIndex);
        assertTrue(productIndex > flakyIndex);
    }

    @Test
    @DisplayName("Verify flaky flag exists")
    void verifyFlakyFlag() {

        assertTrue(categories.contains("\"flaky\": true"));
    }

    @Test
    @DisplayName("Verify only one flaky flag")
    void verifyOnlyOneFlakyFlag() {

        int count = categories.split("\"flaky\"").length - 1;

        assertEquals(1, count);
    }

    @Test
    @DisplayName("Verify Flaky category uses failed and broken status")
    void verifyFlakyStatuses() {

        assertTrue(
                categories.contains("\"matchedStatuses\": [\"failed\",\"broken\"]"));
    }

    @Test
    @DisplayName("Verify Broken category uses broken status")
    void verifyBrokenStatus() {

        assertTrue(
                categories.contains("\"matchedStatuses\": [\"broken\"]"));
    }

    @Test
    @DisplayName("Verify Product category uses failed status")
    void verifyProductStatus() {

        assertTrue(
                categories.contains("\"matchedStatuses\": [\"failed\"]"));
    }

    @Test
    @DisplayName("Verify messageRegex exists")
    void verifyRegexExists() {

        assertTrue(categories.contains("\"messageRegex\""));
    }

    @Test
    @DisplayName("Verify timeout regex exists")
    void verifyTimeoutRegex() {

        assertTrue(categories.contains("timeout"));
    }

    @Test
    @DisplayName("Verify stale element regex exists")
    void verifyStaleRegex() {

        assertTrue(categories.contains("stale element"));
    }

    @Test
    @DisplayName("Verify connection reset regex exists")
    void verifyConnectionResetRegex() {

        assertTrue(categories.contains("connection reset"));
    }

    @Test
    @DisplayName("Verify category names are unique")
    void verifyUniqueCategoryNames() {

        assertEquals(
                categories.indexOf("\"Flaky tests\""),
                categories.lastIndexOf("\"Flaky tests\""));

        assertEquals(
                categories.indexOf("\"Test defects {broken}\""),
                categories.lastIndexOf("\"Test defects {broken}\""));

        assertEquals(
                categories.indexOf("\"Product defects\""),
                categories.lastIndexOf("\"Product defects\""));
    }

    @Test
    @DisplayName("Verify all required category names exist")
    void verifyAllCategoryNames() {

        assertAll(
                () -> assertTrue(categories.contains("\"Flaky tests\"")),
                () -> assertTrue(categories.contains("\"Test defects {broken}\"")),
                () -> assertTrue(categories.contains("\"Product defects\""))
        );
    }

    @Test
    @DisplayName("Verify failed status appears twice")
    void verifyFailedStatusCount() {

        int count = categories.split("\"failed\"").length - 1;

        assertEquals(2, count);
    }

    @Test
    @DisplayName("Verify broken status appears twice")
    void verifyBrokenStatusCount() {

        int count = categories.split("\"broken\"").length - 1;

        assertEquals(2, count);
    }

    @Test
    @DisplayName("Verify messageRegex contains expected patterns")
    void verifyRegexPatterns() {

        assertAll(
                () -> assertTrue(categories.contains("timeout")),
                () -> assertTrue(categories.contains("stale element")),
                () -> assertTrue(categories.contains("connection reset"))
        );
    }

    @Test
    @DisplayName("Verify categories contain no TODO")
    void verifyNoTodo() {

        assertFalse(categories.contains("TODO"));
    }

    @Test
    @DisplayName("Verify file contains no empty category names")
    void verifyNoEmptyCategoryName() {

        assertFalse(categories.contains("\"name\": \"\""));
    }

    @Test
    @DisplayName("Verify file contains three matchedStatuses")
    void verifyMatchedStatusesCount() {

        int count = categories.split("\"matchedStatuses\"").length - 1;

        assertEquals(3, count);
    }
    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Categories split flaky, test and product failures so each bucket has an error")
    void categoriesSpecificFlakyRuleBeforeGenericBuckets() throws IOException
    {
        String categories  = Files.readString(Path.of("src/test/resources/allure/categories.json"));

        int flakyIndex = categories.indexOf("\"Flaky tests\"");
        int testDefectIndex = categories.indexOf("\"Test defects {broken}\"");
        int productDefectIndex = categories.indexOf("\"Product defects\"");

        assertTrue(flakyIndex>=0,"Flaky category must exist");
        assertTrue(testDefectIndex > flakyIndex,"Specific flaky rule must run before gen");
        assertTrue(productDefectIndex > flakyIndex ,"Specific flaky rule must fun before");
        assertTrue(categories.contains("\"flaky\": true"));
    }
}
