package com.mywebstore.users;

import com.mywebstore.users.model.UserModel;
import io.cucumber.java8.En;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

public class UserStepDefinitions extends UsersClientsServiceApplicationTests implements En {
    public UserStepDefinitions() {
        Given("no users in DB", () -> {
            clean();
            assertThat(getUsers()).isEqualTo(0);
        });
        When("user tries to login with username and password", (String username, String password) -> {
            executeLogin(username,password);
        });
        Then("function sends back an error code (\\d+)", (Integer statusCode) -> {
            HttpStatus currentStatusCode = responseMessage.getCode();
            String message = responseMessage.getMessage();
            assertThat(message+currentStatusCode.value()).isEqualTo("status code is incorrect : "+statusCode);
            //assertThat("status code is incorrect : "+latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
        });
        Given("no user in DB", () -> {
            clean();
            assertThat(getUsers()).isEqualTo(0);
        });
        When("user send data to create user", () -> {
            UserModel user = new UserModel("Aitor","Subirat", 666666666L,"12345678-G","Xancros",1234);
            executeCreateUser(user);

        });
        Then("DB creates user", () -> {
            assertThat(getUsers()).isEqualTo(1);
        });
        And("returns code (\\d+)", (Integer statusCode) -> {
            HttpStatus currentStatusCode = responseMessage.getCode();
            assertThat(currentStatusCode.value()).isEqualTo(statusCode);
        });
    }
}
