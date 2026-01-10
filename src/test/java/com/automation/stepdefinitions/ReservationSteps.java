package com.automation.stepdefinitions;

import com.automation.pages.CustomersPage;
import com.automation.pages.DashboardPage;
import com.automation.pages.LoginPage;
import com.automation.pages.ReservationPage;
import com.automation.utils.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Step Definitions for Reservation and Customer creation scenarios
 */
public class ReservationSteps {
    private static final Logger logger = LogManager.getLogger(ReservationSteps.class);

    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final ReservationPage reservationPage = new ReservationPage();
    private final CustomersPage customersPage = new CustomersPage();

    // Store customer data for verification
    private String customerFirstName;
    private String customerLastName;

    // ==================== When Steps ====================

    @When("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() {
        String email = ConfigReader.getTestUserEmail();
        String password = ConfigReader.getTestUserPassword();
        loginPage.login(email, password);
        logger.info("Logged in with valid credentials: {}", email);

        // Wait for dashboard to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("Dashboard should be loaded after login", dashboardPage.isPageLoaded());
    }

    @When("the user navigates to the new reservation page")
    public void theUserNavigatesToTheNewReservationPage() {
        dashboardPage.navigateToReservations();

        // Wait for page transition
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue("Reservation page should be loaded", reservationPage.isPageLoaded());
        reservationPage.clickNewReservation();

        // Wait for vehicle list to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        reservationPage.selectFirstAvailableVehicle();
        logger.info("Navigated to new reservation page and selected vehicle");
    }

    @When("the user clicks on the add customer button")
    public void theUserClicksOnTheAddCustomerButton() {
        // Wait for reservation form to fully load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        reservationPage.clickAddCustomer();
        logger.info("Clicked on add customer button");
    }

    @When("the user enters customer information {string}, {string}, {string}, {string}")
    public void theUserEntersCustomerInformation(String firstName, String lastName, String phone, String email) {
        // Store for later verification
        this.customerFirstName = firstName;
        this.customerLastName = lastName;

        reservationPage.enterCustomerInformation(firstName, lastName, phone, email);
        logger.info("Entered customer information: {} {} - {} - {}", firstName, lastName, phone, email);
    }

    @When("the user clicks the create customer button")
    public void theUserClicksTheCreateCustomerButton() {
        reservationPage.clickCreateCustomer();
        logger.info("Clicked create customer button");

        // Wait for customer to be created
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @When("the user closes the reservation form")
    public void theUserClosesTheReservationForm() {
        reservationPage.closeReservationForm();
        logger.info("Closed reservation form");

        // Wait for form to close
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @When("the user navigates to the customers page")
    public void theUserNavigatesToTheCustomersPage() {
        dashboardPage.navigateToCustomers();
        logger.info("Navigated to customers page");

        // Wait for page to load
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue("Customers page should be loaded", customersPage.isPageLoaded());
    }

    // ==================== Then Steps ====================

    @Then("the customer should be created successfully")
    public void theCustomerShouldBeCreatedSuccessfully() {
        assertTrue("Customer should be created and visible in the list",
                reservationPage.isCustomerCreatedSuccessfully(customerFirstName, customerLastName));
        logger.info("Verified customer {} {} was created successfully", customerFirstName, customerLastName);
    }

    @Then("the customer {string} should be visible in the customers list")
    public void theCustomerShouldBeVisibleInTheCustomersList(String fullName) {
        assertTrue("Customer '" + fullName + "' should be visible in the customers table",
                customersPage.isCustomerVisibleByFullName(fullName));
        logger.info("Verified customer '{}' is visible in the customers list", fullName);
    }
}
