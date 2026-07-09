package com.ust.sdet.bbd;


import com.ust.sdet.pages.LoginPage;
import com.ust.sdet.support.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoginSteps {

    WebDriver driver = DriverFactory.createDriver();
    LoginPage loginPage;
    String welcomeText;
    String errorText;

    @Given("user is on the login page")
    public void user_is_on_login_page() {
        loginPage = new LoginPage(driver)
                .openSignPage();
    }

    @When("user logs in with email {string} and password {string}")
    public void user_logs_in_with_email_and_password(String email, String password) {
        loginPage = loginPage.login(email, password);
    }

    @Then("user should see welcome message {string}")
    public void user_should_see_welcome_message(String expectedMessage) {
        loginPage.isOnHomePage();
        welcomeText = loginPage.header()
                .login_component()
                .getWelcomeTitle();

        assertTrue(welcomeText.contains(expectedMessage));
    }

    @Then("user should see error message {string}")
    public void user_should_see_error_message(String expectedMessage) {
         loginPage.isOnLoginPage();
        errorText = loginPage.getErrorMessage();
        assertEquals(expectedMessage,errorText);
        assertTrue(loginPage.isOnLoginPage());
    }
}