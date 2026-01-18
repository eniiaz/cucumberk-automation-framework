@nomadtms @ui @epic-Authentication @feature-Login
Feature: Nomad TMS User Login Functionality
  As a fleet manager
  I want to be able to log in to the Nomad TMS system
  So that I can access fleet operations and management features

  Background:
    Given the Nomad TMS user is on the login page

  @positive @smoke @dothis
  Scenario: Successful login with valid credentials
    When the Nomad TMS user enters email "sdet@gmail.com"
    And the Nomad TMS user enters password "Tester"
    And the Nomad TMS user clicks the sign in button
    Then the Nomad TMS user should be redirected to the dashboard

  @negative @smoke
  Scenario: Login with invalid password
    When the Nomad TMS user enters email "sdet@gmail.com"
    And the Nomad TMS user enters password "wrongpassword"
    And the Nomad TMS user clicks the sign in button
    Then the Nomad TMS user should see a login error message
    And the Nomad TMS user should remain on the login page

  @negative @smoke
  Scenario: Login with non-existent email
    When the Nomad TMS user enters email "nonexistent@gmail.com"
    And the Nomad TMS user enters password "Tester"
    And the Nomad TMS user clicks the sign in button
    Then the Nomad TMS user should see a login error message
    And the Nomad TMS user should remain on the login page

  @negative
  Scenario: Login with empty email
    When the Nomad TMS user enters password "Tester"
    And the Nomad TMS user clicks the sign in button
    Then the Nomad TMS user should remain on the login page

  @negative
  Scenario: Login with empty password
    When the Nomad TMS user enters email "sdet@gmail.com"
    And the Nomad TMS user clicks the sign in button
    Then the Nomad TMS user should remain on the login page

  @negative
  Scenario: Login with invalid email format
    When the Nomad TMS user enters email "invalid-email"
    And the Nomad TMS user enters password "Tester"
    And the Nomad TMS user clicks the sign in button
    Then the Nomad TMS user should remain on the login page

  @positive
  Scenario Outline: Login with different user types
    When the Nomad TMS user enters email "<email>"
    And the Nomad TMS user enters password "<password>"
    And the Nomad TMS user clicks the sign in button
    Then the Nomad TMS user should be redirected to the dashboard

    Examples:
      | email          | password |
      | sdet@gmail.com | Tester   |
