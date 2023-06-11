Feature: Money deposit in a wallet

  Scenario: Deposit money into an available wallet
    Given a valid user is available
    And valid user credentials are sent
    And a valid wallet is available
    When user deposit money into it
    Then response code is "200"

  Scenario: Deposit money into a not existing wallet
    Given a valid user is available
    And valid user credentials are sent
    When user deposit money into it
    Then response code is "400"
    And response body is:
      """
      {"message":"Could not find the requested resource: Wallet with id 26929514-237c-11ed-861d-0242ac120001"}
      """