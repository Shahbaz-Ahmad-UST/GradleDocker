package com.ust.sdet.pages;

import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private static final By EMAIL = By.cssSelector("[id='email']");
    private  static final By PASSWORD=By.cssSelector("[id='password']");
    private static final By SIGN_IN =
            By.cssSelector("#main-content > section > form > button");
    private  static final By LOGIN_PAGE_TITLE=By.cssSelector("[id='login-title']");
    private static final By TITLE = By.cssSelector("[id='page-title']");
    private static final By SIGN_OUT =
            By.cssSelector("#main-content > section.hero > div.hero-copy > div > button");
    private static final By INVALID_LOGIN = By.cssSelector("[data-testid='login-error']");


    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public LoginPage openSignPage()
    {
      driver.get(Config.loginUrl());
      visible(LOGIN_PAGE_TITLE);
      wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_PAGE_TITLE));
      return this;
    }

    public  LoginPage login(String email, String password) {
        driver.findElement(EMAIL).sendKeys(email);
        driver.findElement(PASSWORD).sendKeys(password);
        click(SIGN_IN);
       return this;
    }

    public LoginPage logout()
    {
        driver.get(Config.homeUrl());
        visible(TITLE);
        click(SIGN_OUT);
        return  this;
    }


    public boolean isOnHomePage() {
        return wait.until(ExpectedConditions.urlContains("/home"));
    }
    public boolean isOnLoginPage()
    {
        return wait.until(ExpectedConditions.urlContains("/login"));
    }

    public String getErrorMessage()
    {
        visible(INVALID_LOGIN);
        return driver.findElement(INVALID_LOGIN).getText();
    }
}
