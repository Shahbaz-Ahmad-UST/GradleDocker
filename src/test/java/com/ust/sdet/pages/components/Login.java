package com.ust.sdet.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
    private static final By TITLE = By.cssSelector("[id='page-title']");
    private  static final By LOGINPAGETITLE=By.cssSelector("[id='login-title']");

    private final WebDriverWait wait;

    public Login(WebDriverWait wait)
    {
        this.wait = wait;
    }


    public String getWelcomeTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TITLE)).getText();
    }

    public String signOut() {
        wait.until(ExpectedConditions.urlContains("/login"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LOGINPAGETITLE)).getText();

    }


}
