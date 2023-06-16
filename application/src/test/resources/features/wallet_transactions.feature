Feature: Visualize transactions from a wallet

  Scenario: Get a couple of transactions and overall balance
    Given a valid user is available
    And a second valid user is available
    And valid user credentials are sent
    And a valid wallet is available
    And user deposit money into it
    And second valid user credentials are sent
    And a second valid wallet is available
    And valid user credentials are sent
    And user transfers money into it
    When user requests wallet transactions with pagination "page=0&size=10"
    Then response code is "200"
    And response json is like:
      """
      {"quantity":10.0,"transactions":[{"destinyWalletId":"26929514-237c-11ed-861d-0242ac120003","type":"TRANSFERENCE","quantity":10.0,"at":x},{"destinyWalletId":"26929514-237c-11ed-861d-0242ac120001","type":"DEPOSIT","quantity":10.0,"at":x}]}
      """

  Scenario: Get one transaction and overall balance
    Given a valid user is available
    And a second valid user is available
    And valid user credentials are sent
    And a valid wallet is available
    And user deposit money into it
    And second valid user credentials are sent
    And a second valid wallet is available
    And valid user credentials are sent
    And user transfers money into it
    When user requests wallet transactions with pagination "page=0&size=1"
    Then response code is "200"
    And response json is like:
      """
      {"quantity":10.0,"transactions":[{"destinyWalletId":"26929514-237c-11ed-861d-0242ac120003","type":"TRANSFERENCE","quantity":10.0,"at":x}]}
      """

