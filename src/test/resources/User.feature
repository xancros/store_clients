Feature: User operations
  Scenario: user attempts to login calling POST /user/login with username and password
    Given no users in DB
    When user tries to login with credentials "aitor" and "1234"
    Then function sends back an error code 204


   Scenario: user knows he is not registered and signs up using /user/register
     Given no user in DB
     When user sends data to create user
     Then DB creates user
     And returns code 201


     Scenario: user wants to delete his account using /user/delete
       When user sends his credentials as "Xancros" and "1234" and his IDCard "12345678G"
       Then function deletes the user and sends back code 418

