package com.iobuilders.user.infrastructure.steps;

import com.iobuilders.shared.utils.ScenarioContext;
import com.iobuilders.shared.utils.http.HttpClient;
import com.iobuilders.shared.utils.http.HttpMethod;
import com.iobuilders.shared.utils.http.HttpRequest;
import com.iobuilders.user.domain.LoginRequestObjectMother;
import com.iobuilders.user.domain.UserObjectMother;
import com.iobuilders.user.domain.dto.LoginRequest;
import com.iobuilders.user.domain.dto.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps {
    private static final String VALID_USER = "valid";
    private static final int DEFAULT_USER_ID = 1;
    private static final int NOT_EXISTING_USER_ID = 40;
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

    @When("^valid user credentials are sent$")
    public void userLoginIsRequested() {
        ResponseEntity<String> response = doLoginRequest();
        context.setResponse(response);
    }

    @When("^(valid|invalid) user delete request is sent$")
    public void userDeleteIsRequested(String requestType) {
        int userID = VALID_USER.equals(requestType) ? DEFAULT_USER_ID : NOT_EXISTING_USER_ID;
        ResponseEntity<String> response = doDeleteRequest(userID);
        context.setResponse(response);
    }

    private ResponseEntity<String> doCreateRequest() {
        User user = UserObjectMother.basic();

        final HttpRequest<User> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/users/register", user);
        return httpClient.doRequest(httpRequest);

    }

    private ResponseEntity<String> doDeleteRequest(int userId) {
        User user = UserObjectMother.basic();

        final HttpRequest<User> httpRequest = HttpClient.createHttpRequest(HttpMethod.DELETE, "/users/" + userId, user);
        return httpClient.doRequest(httpRequest);
    }

    private ResponseEntity<String> doLoginRequest() {
        LoginRequest request = LoginRequestObjectMother.basic();

        final HttpRequest<LoginRequest> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/users/login", request);
        return httpClient.doRequest(httpRequest);
    }

}
