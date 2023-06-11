package com.iobuilders.steps;


import com.iobuilders.ScenarioContext;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserObjectMother;
import com.iobuilders.http.HttpClient;
import com.iobuilders.http.HttpMethod;
import com.iobuilders.http.HttpRequest;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class UserSteps {
    private static final String VALID_USER = "valid";
    private static final String DEFAULT_USER_ID = "26929514-237c-11ed-861d-0242ac120002";
    private static final String NOT_EXISTING_USER_ID = "66929514-237c-11ed-861d-0242ac120002";
    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ScenarioContext context;


    @When("^(valid|invalid) user delete request is sent$")
    public void userDeleteIsRequested(String requestType) {
        String userID = VALID_USER.equals(requestType) ? DEFAULT_USER_ID : NOT_EXISTING_USER_ID;
        ResponseEntity<String> response = doDeleteRequest(userID);
        context.setResponse(response);
    }

    private ResponseEntity<String> doDeleteRequest(String userId) {
        User user = UserObjectMother.basic();

        final HttpRequest<User> httpRequest = HttpClient.createHttpRequest(HttpMethod.DELETE, "/users/" + userId, user);
        return httpClient.doRequest(httpRequest);
    }

}
