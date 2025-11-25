
Feature: Database CRUD Operations

  Scenario: Create, Read, Update, Delete record in TestPerson table
    Given I insert a new person record
    When I update the person record
    Then I verify the updated person record
   # And I delete the person record
    Then the person record should not exist

    
    Scenario: Do CRUD on exisitng Adventureworks db
    Given I select a record from table
    When Update specific ids data from table
    Then select employee who is single