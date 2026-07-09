package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private static final By PLACE_ORDER = By.cssSelector("[data-test = 'place-order']");
    private static final By CONFIRMATION = By.cssSelector("[data-test = 'order-confirmation']");

    private static final By TOTAL =
            By.cssSelector("[data-testid='checkout-total']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }


    public String getTotal(){
        return text(TOTAL);
    }
    public CheckoutPage placeOrder()
    {
        click(PLACE_ORDER);
        visible(CONFIRMATION);
        return this;
    }
    public String confirmationText()
    {
        return text(CONFIRMATION);
    }
}
