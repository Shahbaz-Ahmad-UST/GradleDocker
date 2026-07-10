package com.ust.sdet.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.extension.*;

public class ExtentTestListener implements
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback {

    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void beforeTestExecution(ExtensionContext context) {

        test.set(extent.createTest(context.getDisplayName()));
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {

        if (context.getExecutionException().isPresent()) {
            test.get().fail(context.getExecutionException().get());
        } else {
            test.get().pass("Passed");
        }

        extent.flush();
    }
}