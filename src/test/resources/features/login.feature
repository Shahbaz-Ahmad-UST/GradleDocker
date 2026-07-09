Feature: Login functionality

  Background:
    Given user is on the login page


  @smoke
  Scenario: Successful login with valid credentials
    When user logs in with email "customer@example.com" and password "Password@123"
    Then user should see welcome message "Welcome, Customer User"

  @smoke
  Scenario: Login with invalid password
    When user logs in with email "customer@example.com" and password "WrongPassword"
    Then user should see error message "Invalid credentials"