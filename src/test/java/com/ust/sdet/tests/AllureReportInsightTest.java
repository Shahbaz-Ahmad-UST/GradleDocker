package com.ust.sdet.tests;


import com.ust.sdet.report.ExtentTestListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Framework Hardening")
@Feature("Reporting Insights")
@Owner("SDET Trainer")
@ExtendWith(ExtentTestListener.class)
public class AllureReportInsightTest {

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
        assertFalse(categories.contains("\"flaky\": true"));
    }
}
