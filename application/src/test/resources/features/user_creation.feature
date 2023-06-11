Feature: User creation use cases

  Scenario: Creating a user with correct values
    When valid user creation request is sent
    Then response code is "201"