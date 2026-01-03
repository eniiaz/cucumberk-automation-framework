@ui  @epic-Authentication @feature-SignUp
Feature: User Sign Up Functionality
  As a new user
  I want to be able to create an account in the MKF CRM system
  So that I can access the car rental management features

  Background:
    Given the user is on the sign up page

  @positive @smoke
  Scenario: Successful sign up with valid credentials
    When the user enters full name "Test User"
    And the user enters signup email "testuser@example.com"
    And the user enters signup password "password123"
    And the user clicks the create account button
    Then the user should be successfully registered

  @negative
  Scenario: Sign up with existing email
    When the user enters full name "Test User"
    And the user enters signup email "esen@gmail.com"
    And the user enters signup password "password123"
    And the user clicks the create account button
    Then the user should see a signup error message
    And the user should remain on the sign up page

  @negative
  Scenario: Sign up with password less than 6 characters
    When the user enters full name "Test User"
    And the user enters signup email "newuser@example.com"
    And the user enters signup password "pass"
    Then the user should see password validation message
    And the create account button should be disabled

  @negative
  Scenario: Sign up with empty full name
    When the user enters signup email "newuser@example.com"
    And the user enters signup password "password123"
    And the user clicks the create account button
    Then the user should remain on the sign up page

  @negative
  Scenario: Sign up with empty email
    When the user enters full name "Test User"
    And the user enters signup password "password123"
    And the user clicks the create account button
    Then the user should remain on the sign up page

  @negative
  Scenario: Sign up with invalid email format
    When the user enters full name "Test User"
    And the user enters signup email "invalid-email"
    And the user enters signup password "password123"
    And the user clicks the create account button
    Then the user should remain on the sign up page
