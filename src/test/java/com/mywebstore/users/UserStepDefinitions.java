package com.mywebstore.users;

import com.mywebstore.users.model.UserModel;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

public class UserStepDefinitions extends UsersClientsServiceApplicationTests implements En {
    public UserStepDefinitions() {
        Given("no users in DB", () -> assertThat(getUsers()).isEqualTo(0));
        When("^user tries to login with credentials \"([^\"]*)\" and \"([^\"]*)\"$", this::executeLogin);
        Then("^function sends back an error code (\\d+)$", (Integer statusCode) -> assertThat(responseMessage.getCode().value()).isEqualTo(statusCode));
        Given("no user in DB", () -> assertThat(getUsers()).isEqualTo(0));
        When("user sends data to create user", () -> {
            UserModel user = new UserModel();
            user.setName("Aitor");
            user.setLastName("Subirat");
            user.setIdCard("12345678G");
            user.setPassword(1234);
            user.setPhone(666666666L);
            user.setUserName("Xancros");
            executeCreateUser(user);

        });
        Then("DB creates user", () -> assertThat(getUsers()).isEqualTo(1));
        And("^returns code (\\d+)$", this::assertCode);
        When("^user sends his credentials as \"([^\"]*)\" and \"([^\"]*)\" and his IDCard \"([^\"]*)\"$", this::executeRemoveUser);
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
                executeCreateUser(user);
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
                        executeModifyUser(user);
                    });

        });
        Then("^the service modify the user in DB and sends back code (\\d+)$", this::assertCode);

    }

}
