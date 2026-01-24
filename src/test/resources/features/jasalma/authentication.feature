@jasalma @ui @epic-Authentication
Feature: Jasalma AI Authentication Functionality
  As a user of Jasalma AI
  I want to be able to sign up and log in
  So that I can access the learning platform

  # Test Data (stored in feature file only - not in properties)
  # URL: https://www.jasalma.ai
  # Login credentials:
  #   email: pro@gmail.com
  #   password: Prodbek2025!

  @positive @smoke @signup
  Scenario: Successful sign up with valid email
    Given the Jasalma user is on the sign up page
    When the Jasalma user enters first name "Test"
    And the Jasalma user enters last name "User"
    And the Jasalma user enters email "testuser@outlook.com"
    And the Jasalma user enters password "TestPassword123!"
    And the Jasalma user clicks the continue button
    Then the Jasalma user should see a verification or success message

  @positive @smoke @login
  Scenario: Successful login and dashboard access verification
    Given the Jasalma user is on the sign in page
    When the Jasalma user enters email "pro@gmail.com"
    And the Jasalma user enters password "Prodbek2025!"
    And the Jasalma user clicks the continue button
    Then the Jasalma user should be logged in successfully
    When the Jasalma user navigates to the dashboard
    Then the Jasalma user should remain on the dashboard page
    And the Jasalma user should not be redirected to the main page
