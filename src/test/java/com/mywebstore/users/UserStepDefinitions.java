package com.mywebstore.users;

import com.mywebstore.users.model.UserModel;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

public class UserStepDefinitions extends UsersClientsServiceApplicationTests implements En {
    protected String jwtToken;
    public UserStepDefinitions() {
        Given("^db has this user stored by map:$", (DataTable data) -> {
            /*UserModel user = new UserModel();
            data.asMaps().forEach(entry -> {
                user.setUserName(entry.get("userName"));
                user.setPhone(Long.parseLong(entry.get("phone")));
                user.setName(entry.get("name"));
                user.setLastName(entry.get("surname"));
                user.setPassword(Integer.parseInt(entry.get("password")));
                user.setIdCard(entry.get("idCard"));
                executeCreateUser(user);
            });*/
        });
        When("^calling /authenticate with credentials \"([^\"]*)\" and \"([^\"]*)\"$", this::executeAuthenticate);
        Then("^it returns a valid jwt token if the credentials are correct$", () -> {
            jwtToken= Objects.requireNonNull(responseEntity.getBody()).getToken();
            assertThat(jwtToken.length()>0);

        });
        Given("^credentials to authenticate \"([^\"]*)\" and \"([^\"]*)\"$", (String username, String password) -> {
            executeAuthenticate(username,password);
            jwtToken= new String(Objects.requireNonNull(responseEntity.getBody()).getToken());
        });
        Given("no users in DB", () -> assertThat(getUsers(jwtToken)).isEqualTo(0));
        When("^user tries to login with credentials \"([^\"]*)\" and \"([^\"]*)\"$", (String username,String password) -> executeLogin(username,password,jwtToken));
        Then("^function sends back an error code (\\d+)$", (Integer statusCode) -> assertThat(responseMessage.getCode().value()).isEqualTo(statusCode));

        Given("no user in DB", () -> assertThat(getUsers(jwtToken)).isEqualTo(0));
        When("user sends data to create user", () -> {
            UserModel user = new UserModel();
            user.setName("Aitor");
            user.setLastName("Subirat");
            user.setIdCard("12345678G");
            user.setPassword(1234);
            user.setPhone(666666666L);
            user.setUserName("Xancros");
            executeCreateUser(user,jwtToken);

        });
        Then("DB creates user", () -> assertThat(getUsers(jwtToken)).isEqualTo(1));
        And("^returns code (\\d+)$", this::assertCode);
        When("^user sends his credentials as \"([^\"]*)\" and \"([^\"]*)\" and his IDCard \"([^\"]*)\"$", (String username, String password, String idCard) ->
                executeRemoveUser(username,password,idCard,jwtToken));
        Then("^function deletes the user and sends back code (\\d+)$", this::assertCode);


        Given("^db has this users stored by map:$", (DataTable data) -> {
            UserModel user = new UserModel();
            data.asMaps().forEach(entry -> {
                user.setUserName(entry.get("userName"));
                user.setPhone(Long.parseLong(entry.get("phone")));
                user.setName(entry.get("name"));
                user.setLastName(entry.get("surname"));
                user.setPassword(Integer.parseInt(entry.get("password")));
                user.setIdCard(entry.get("idCard"));
                executeCreateUser(user,jwtToken);
            });
        });
        When("^calls /modify with the data$", (DataTable data) -> {
            UserModel user = new UserModel();
            data.asMaps().forEach(entry -> {
                        user.setUserName(entry.get("userName"));
                        user.setPhone(Long.parseLong(entry.get("phone")));
                        user.setName(entry.get("name"));
                        user.setLastName(entry.get("surname"));
                        user.setPassword(Integer.parseInt(entry.get("password")));
                        user.setIdCard(entry.get("idCard"));
                        executeModifyUser(user,jwtToken);
                    });

        });
        Then("^the service modify the user in DB and sends back code (\\d+)$", this::assertCode);



    }

}
