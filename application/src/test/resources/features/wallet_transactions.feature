Feature: Visualize transactions from a wallet

  Scenario: Get a couple of transactions and overall balance
    Given a valid user is available
    And valid user credentials are sent
    And a valid wallet is available
    And user deposit money into it
    And an external valid wallet is available
    And user transfers money into it
    When user requests wallet transactions
    Then response code is "200"
    And response json is like:
      """
      {"quantity":10.0,"transactions":[{"destinyWalletId":"26929514-237c-11ed-861d-0242ac120003","type":"TRANSFERENCE","quantity":10.0,"at":x},{"destinyWalletId":"26929514-237c-11ed-861d-0242ac120001","type":"DEPOSIT","quantity":10.0,"at":x}]}
      """

