Feature: User operations
  Scenario: user attempts to login calling POST /user/login with username and password
    Given no users in DB
    When user tries to login with username and password
    Then function sends back an error code 500


   Scenario: user knows he is not registered and signs up using /user/register
     Given no user in DB
     When user send data to create user
     Then DB creates user
     And returns code 1

