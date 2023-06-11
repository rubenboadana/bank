Feature: Wallet creation use cases

  Scenario: Creating a wallet with correct values
    Given a valid user is available
    And valid user credentials are sent
    When valid wallet creation request is sent
    Then response code is "201"