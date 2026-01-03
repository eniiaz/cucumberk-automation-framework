@ui @epic-Invoicing @feature-InvoiceGeneration
Feature: Invoice Generation from Reservations
  As a user
  I want to generate invoices for reservations
  So that I can provide customers with payment documents

  Background:
    Given the user is logged in to the system
    And the user has an existing reservation

  @positive @smoke @MC-150 @MC-157
  Scenario: Generate invoice from valid reservation with auto-generated invoice number
    When the user navigates Accounting page
    And the user clicks on Income tab
    And the user clicks the generate invoice button
    Then user clicks on from reservation tab on the new invoice form
    Then user selects one reservation from the list
    And the invoice should have an auto-generated invoice number
    And the invoice status should be "pending"
   

  @positive @MC-153 @MC-154 @MC-155 @MC-156
  Scenario Outline: Generate invoice with different invoice types
    When the user navigates to the reservation details page
    And the user selects invoice type "<invoice_type>"
    And the user clicks the generate invoice button
    Then an invoice of type "<invoice_type>" should be created
    And the invoice should display the correct type label "<type_label>"

    Examples:
      | invoice_type | type_label   |
      | deposit      | Deposit      |
      | balance      | Balance      |
      | full         | Full Payment |
      | credit_note  | Credit Note  |

  @positive @MC-158 @MC-159 @MC-160
  Scenario Outline: Validate invoice status tracking
    Given the user has an invoice with status "<initial_status>"
    When the user updates the invoice status to "<new_status>"
    Then the invoice status should be "<new_status>"
    And the status change should be recorded in the invoice history

    Examples:
      | initial_status | new_status |
      | pending        | paid       |
      | pending        | cancelled  |
      | paid           | cancelled  |

