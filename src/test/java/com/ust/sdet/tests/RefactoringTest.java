//package com.ust.sdet.tests;
//
//import com.ust.sdet.pages.CartPage;
//import com.ust.sdet.pages.CatalogPage;
//import com.ust.sdet.pages.LoginPage;
//import com.ust.sdet.support.Config;
//import com.ust.sdet.support.DriverFactory;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.WebDriver;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class RefactoringTest {
//
//    private WebDriver driver;
//
//    @BeforeEach
//    void setup() {
//
//        driver = DriverFactory.createChromeDriver();
//        driver.get(Config.baseUrl());
//    }
//
//
//    @AfterEach
//    void tearDown() {
//
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//
//    @Test
//    @DisplayName("Exercise 1")
//    void buyProduct() {
//
//        LoginPage home =
//                new LoginPage(driver).openSignPage()
//                        .login("customer@example.com", "Password@123");
//
//        assertEquals("Welcome, Customer User", home.header().login_component().getWelcomeTitle());
//
//
//        CartPage cart =
//                new CatalogPage(driver).open()
//                        .searchFor("headphones")
//                        .openFirstProduct()
//                        .addToCart();
//
//        assertEquals(1, cart.lineCount());
//        assertEquals("Rs. 7,999",cart.total());
//    }
//
//    @Test
//    @DisplayName("Exercise 3")
//    void exercise3()
//    {
//
//    }
//}