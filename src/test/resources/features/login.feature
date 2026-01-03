@ui  @epic-Authentication @feature-Login
Feature: User Login Functionality
  As a user
  I want to be able to log in to the MKF CRM system
  So that I can access my account and manage car rentals

  Background:
    Given the user is on the login page

  @positive @smoke
  Scenario: Successful login with valid credentials
    When the user enters email "esen@gmail.com"
    And the user enters password "Tester123"
    And the user clicks the sign in button
    Then the user should be redirected to the dashboard page

  @negative @smoke
  Scenario: Login with invalid password
    When the user enters email "esen@gmail.com"
    And the user enters password "wrongpassword"
    And the user clicks the sign in button
    Then the user should see an error message
    And the user should remain on the login page

  @negative @smoke
  Scenario: Login with empty email
    When the user enters password "password123"
    And the user clicks the sign in button
    Then the user should remain on the login page

  @negative
  Scenario: Login with empty password
    When the user enters email "esen@gmail.com"
    And the user clicks the sign in button
    Then the user should remain on the login page

  @negative
  Scenario: Login with invalid email format
    When the user enters email "invalid-email"
    And the user enters password "password123"
    And the user clicks the sign in button
    Then the user should remain on the login page
