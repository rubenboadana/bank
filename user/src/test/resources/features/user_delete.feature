Feature: User delete use cases

  Scenario: Deleting a user with correct values
    Given a valid user is available
    When valid user delete request is sent
    Then response code is "200"

  Scenario: Deleting a user not found
    Given a valid user is available
    When invalid user delete request is sent
    Then response code is "404"
    And response body is:
      """
      {"message":"Could not find the requested resource: User with id 40"}
      """