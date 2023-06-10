Feature: User login use cases

  Scenario: Login a user with correct values
    Given a valid user is available
    When valid user credentials are sent
    Then response code is "200"
    And response body is like:
      """
      {"token":"eyJhbGciOiJIUzI1NiJ9
      """

  Scenario: Login a user with unexisting values
    When valid user credentials are sent
    Then response code is "404"
    And response body is:
      """
      {"message":"Could not find the requested resource: User with id rubenboada"}
      """