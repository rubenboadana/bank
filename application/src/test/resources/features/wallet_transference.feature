Feature: Money transference from one to another wallet

  Scenario: Deposit money from one to another available wallets
    Given a valid user is available
    And a second valid user is available
    And valid user credentials are sent
    And a valid wallet is available
    And second valid user credentials are sent
    And a second valid wallet is available
    And valid user credentials are sent
    When user transfers money into it
    Then response code is "201"

  Scenario: Deposit money from one external wallet to another available wallet
    Given a valid user is available
    And a second valid user is available
    And valid user credentials are sent
    And a valid wallet is available
    And second valid user credentials are sent
    And a second valid wallet is available
    When user transfers money into it
    Then response code is "400"
    And response body is:
      """
      {"message":"Invalid credentials provided for the user rubenboada22222"}
      """