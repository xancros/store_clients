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


     Scenario: user wants to modify some information
       Given db has this users stored by map:
       | idCard | surname | name | password | phone | userName |
       |12345678-G|Subirat |Aitor | 1234     |666666666| Xancros|
       |23456789-Z|ASDFG | FFGDS|   5678     |777777777| Zellex |
       |34567890-L|TESTING |TEST |  1111    |888888888|  test_testing|
       When calls /modify with the data
         | idCard | surname | name | password | phone | userName |
         | 12345678-G | Subirat | Aitor | 666 | 2390377 | Xancros |
       Then the service modify the user in DB and sends back code 202

