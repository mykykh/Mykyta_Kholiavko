Feature: Managing work shifts
  Scenario: Adding and deleting new record
    Given I am on the Login page
    When I logging in
    And I go to Admin page
    And I select job - work shifts
    And I click on the Add button
    And I enter work shift name "test", from "06:00 AM", to "06:00 PM", employee "Odis Adalwin"
    Then I should see my work shift added
    When I click Delete Selected button
    And I click Yes, Delete button
    Then I should not see my work shift

