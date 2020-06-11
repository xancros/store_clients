Feature: User operations
  Scenario: User authenticates calling /authenticate and giving a valid user
    Given db has this user stored by map:
      |userName|roles|password|active|
      |Xancros|ROLE_ADMIN|12345|True|
    When calling /authenticate with credentials "Zellex" and "12345"
    Then it returns a valid jwt token if the credentials are correct


  Scenario: user attempts to login calling POST /user/login with username and password
    Given credentials to authenticate "Zellex" and "12345"
    Given no users in DB
    When user tries to login with credentials "aitor" and "1234"
    Then function sends back an error code 204


   Scenario: user knows he is not registered and signs up using /user/register
     Given credentials to authenticate "Zellex" and "12345"
     Given no user in DB
     When user sends data to create user
     Then DB creates user
     And returns code 201


     Scenario: user wants to delete his account using /user/delete
       Given credentials to authenticate "Zellex" and "12345"
       When user sends his credentials as "Xancros" and "1234" and his IDCard "12345678G"
       Then function deletes the user and sends back code 418


     Scenario: user wants to modify some information
       Given credentials to authenticate "Zellex" and "12345"
       Given db has this users stored by map:
       | idCard | surname | name | password | phone | userName |
       |12345678-G|Subirat |Aitor | 1234     |666666666| Xancros|
       |23456789-Z|ASDFG | FFGDS|   5678     |777777777| Zellex |
       |34567890-L|TESTING |TEST |  1111    |888888888|  test_testing|
       When calls /modify with the data
         | idCard | surname | name | password | phone | userName |
         | 12345678-G | Subirat | Aitor | 666 | 2390377 | Xancros |
       Then the service modify the user in DB and sends back code 202

