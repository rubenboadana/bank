Feature: Money transference from one to another wallet

  Scenario: Deposit money from one to another available wallets
    Given a valid user is available
    And valid user credentials are sent
    And a valid wallet is available
    And an external valid wallet is available
    When user transfers money into it
    Then response code is "201"

  Scenario: Deposit money from one to another not existing wallet
    Given a valid user is available
    And valid user credentials are sent
    And a valid wallet is available
    When user transfers money into it
    Then response code is "400"
    And response body is:
      """
      {"message":"Could not find the requested resource: Wallet with id 26929514-237c-11ed-861d-0242ac120003"}
      """