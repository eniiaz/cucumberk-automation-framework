@ui @reservations
Feature: Creating a new customer from reservations page

  As a user
  I want to be able to create a new customer while making a reservation
  So that I can quickly add customer information without leaving the reservation workflow

  @smoke @positive @this
  Scenario Outline: Successfully create a new customer from the new reservation page
    Given the user is on the login page
    When the user logs in with valid credentials
    And the user navigates to the new reservation page
    And the user clicks on the add customer button
    And the user enters customer information "<firstName>", "<lastName>", "<phone>", "<email>"
    And the user clicks the create customer button
    Then the customer should be created successfully
    When the user closes the reservation form
    And the user navigates to the customers page
    Then the customer "<firstName> <lastName>" should be visible in the customers list

    Examples:
      | firstName | lastName | phone        | email              |
      | John      | Doe      | 555-123-4567 | john.doe@email.com |
      | John      | Doe      | 555-123-4567 | john.doe@email.com |
