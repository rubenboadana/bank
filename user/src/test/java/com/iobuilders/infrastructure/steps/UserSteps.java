package com.iobuilders.infrastructure.steps;


import com.iobuilders.ScenarioContext;
import com.iobuilders.domain.LoginRequestObjectMother;
import com.iobuilders.domain.UserObjectMother;
import com.iobuilders.domain.dto.LoginRequest;
import com.iobuilders.domain.dto.User;
import com.iobuilders.http.HttpClient;
import com.iobuilders.http.HttpMethod;
import com.iobuilders.http.HttpRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps {
    private static final String VALID_USER = "valid";
    private static final String DEFAULT_USER_ID = "26929514-237c-11ed-861d-0242ac120002";
    private static final String NOT_EXISTING_USER_ID = "66929514-237c-11ed-861d-0242ac120002";
    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ScenarioContext context;

    @Given("a valid user is available")
    public void userIsAlreadyCreated() {
        ResponseEntity<String> response = doCreateRequest();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @When("^valid user creation request is sent$")
    public void userCreationIsRequested() {
        ResponseEntity<String> response = doCreateRequest();
        context.setResponse(response);
    }

    @When("^(valid|invalid) user credentials are sent$")
    public void userLoginIsRequested(String requestType) {
        String password = VALID_USER.equals(requestType) ? UserObjectMother.DEFAULT_PASSWORD : UserObjectMother.INCORRECT_PASSWORD;
        ResponseEntity<String> response = doLoginRequest(password);
        context.setResponse(response);
    }

    @When("^(valid|invalid) user delete request is sent$")
    public void userDeleteIsRequested(String requestType) {
        String userID = VALID_USER.equals(requestType) ? DEFAULT_USER_ID : NOT_EXISTING_USER_ID;
        ResponseEntity<String> response = doDeleteRequest(userID);
        context.setResponse(response);
    }

    private ResponseEntity<String> doCreateRequest() {
        User user = UserObjectMother.basic();

        final HttpRequest<User> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/users/register", user);
        return httpClient.doRequest(httpRequest);

    }

    private ResponseEntity<String> doDeleteRequest(String userId) {
        User user = UserObjectMother.basic();

        final HttpRequest<User> httpRequest = HttpClient.createHttpRequest(HttpMethod.DELETE, "/users/" + userId, user);
        return httpClient.doRequest(httpRequest);
    }

    private ResponseEntity<String> doLoginRequest(String password) {
        LoginRequest request = LoginRequestObjectMother.withPassword(password);

        final HttpRequest<LoginRequest> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/users/login", request);
        return httpClient.doRequest(httpRequest);
    }

}
