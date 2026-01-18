@nomadtms @ui @epic-Authentication @feature-SignUp
Feature: Nomad TMS User Sign Up Functionality
  As a new fleet manager
  I want to be able to create an account in the Nomad TMS system
  So that I can start managing my fleet operations

  Background:
    Given the Nomad TMS user is on the signup page

  @positive @smoke
  Scenario: Successful sign up with valid information
    When the Nomad TMS user enters first name "John"
    And the Nomad TMS user enters last name "Doe"
    And the Nomad TMS user enters organization name "Test Fleet LLC"
    And the Nomad TMS user enters signup email "newuser@testfleet.com"
    And the Nomad TMS user enters signup password "SecurePass123"
    And the Nomad TMS user clicks the create account button
    Then the Nomad TMS user should be successfully registered

  @negative
  Scenario: Sign up with existing email
    When the Nomad TMS user enters first name "Test"
    And the Nomad TMS user enters last name "User"
    And the Nomad TMS user enters organization name "Test Organization"
    And the Nomad TMS user enters signup email "sdet@gmail.com"
    And the Nomad TMS user enters signup password "TestPass123"
    And the Nomad TMS user clicks the create account button
    Then the Nomad TMS user should see a signup error message
    And the Nomad TMS user should remain on the signup page

  @negative
  Scenario: Sign up with empty first name
    When the Nomad TMS user enters last name "User"
    And the Nomad TMS user enters organization name "Test Organization"
    And the Nomad TMS user enters signup email "newuser@example.com"
    And the Nomad TMS user enters signup password "TestPass123"
    And the Nomad TMS user clicks the create account button
    Then the Nomad TMS user should see validation error for first name

  @negative
  Scenario: Sign up with empty last name
    When the Nomad TMS user enters first name "Test"
    And the Nomad TMS user enters organization name "Test Organization"
    And the Nomad TMS user enters signup email "newuser@example.com"
    And the Nomad TMS user enters signup password "TestPass123"
    And the Nomad TMS user clicks the create account button
    Then the Nomad TMS user should see validation error for last name

  @negative
  Scenario: Sign up with empty organization name
    When the Nomad TMS user enters first name "Test"
    And the Nomad TMS user enters last name "User"
    And the Nomad TMS user enters signup email "newuser@example.com"
    And the Nomad TMS user enters signup password "TestPass123"
    And the Nomad TMS user clicks the create account button
    Then the Nomad TMS user should see validation error for organization

  @negative
  Scenario: Sign up with empty email
    When the Nomad TMS user enters first name "Test"
    And the Nomad TMS user enters last name "User"
    And the Nomad TMS user enters organization name "Test Organization"
    And the Nomad TMS user enters signup password "TestPass123"
    And the Nomad TMS user clicks the create account button
    Then the Nomad TMS user should see validation error for email

  @negative
  Scenario: Sign up with invalid email format
    When the Nomad TMS user enters first name "Test"
    And the Nomad TMS user enters last name "User"
    And the Nomad TMS user enters organization name "Test Organization"
    And the Nomad TMS user enters signup email "invalid-email"
    And the Nomad TMS user enters signup password "TestPass123"
    And the Nomad TMS user clicks the create account button
    Then the Nomad TMS user should see validation error for email format

  @negative
  Scenario: Sign up with empty password
    When the Nomad TMS user enters first name "Test"
    And the Nomad TMS user enters last name "User"
    And the Nomad TMS user enters organization name "Test Organization"
    And the Nomad TMS user enters signup email "newuser@example.com"
    And the Nomad TMS user clicks the create account button
    Then the Nomad TMS user should see validation error for password

  @negative
  Scenario: Sign up with weak password
    When the Nomad TMS user enters first name "Test"
    And the Nomad TMS user enters last name "User"
    And the Nomad TMS user enters organization name "Test Organization"
    And the Nomad TMS user enters signup email "newuser@example.com"
    And the Nomad TMS user enters signup password "123"
    And the Nomad TMS user clicks the create account button
    Then the Nomad TMS user should see password strength validation message

  @positive
  Scenario: Navigate between sign in and sign up tabs
    Then the Nomad TMS user should see the sign in tab
    And the Nomad TMS user should see the sign up tab
    When the Nomad TMS user clicks the sign in tab
    Then the Nomad TMS user should see the sign in form
    When the Nomad TMS user clicks the sign up tab
    Then the Nomad TMS user should see the sign up form
