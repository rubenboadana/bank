package com.iobuilders.steps;

import com.iobuilders.ScenarioContext;
import com.iobuilders.domain.dto.LoginRequest;
import com.iobuilders.domain.dto.LoginRequestObjectMother;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserObjectMother;
import com.iobuilders.http.HttpClient;
import com.iobuilders.http.HttpMethod;
import com.iobuilders.http.HttpRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {

    public static final String DEFAULT_PASSWORD = "rubenboada";
    public static final String INCORRECT_PASSWORD = "rubenboadaAAAAA";
    private static final String VALID_USER = "valid";
    @Autowired
    private HttpClient httpClient;
    @Autowired
    private ScenarioContext context;

    public static String parseToken(String response) throws ParseException {
        Object obj = new JSONParser().parse(response);
        JSONObject jo = (JSONObject) obj;

        Object token = jo.get("token");
        if (token == null) {
            return "";
        }

        return token.toString();
    }

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

    @Then("^response code is \"([^\"]*)\"$")
    public void checkResponseCode(int expectedCode) {
        ResponseEntity<String> response = context.getResponse();
        assertThat(response.getStatusCode().value()).isEqualTo(expectedCode);
    }

    @Then("^response body is:$")
    public void checkResponseBody(String expectedBody) {
        ResponseEntity<String> response = context.getResponse();
        assertThat(response.getBody()).isEqualTo(expectedBody);
    }

    @Then("^response body is like:$")
    public void checkSimilarResponseBody(String expectedBody) {
        ResponseEntity<String> response = context.getResponse();
        assertThat(response.getBody()).startsWith(expectedBody);
    }

    @When("^valid user credentials are sent$")
    public void userLoginIsRequested() throws ParseException {
        String password = DEFAULT_PASSWORD;
        ResponseEntity<String> response = doLoginRequest(password);
        context.setResponse(response);
        context.setToken(parseToken(response.getBody()));
    }

    @When("^invalid user credentials are sent$")
    public void userInvalidLoginIsRequested() {
        String password = INCORRECT_PASSWORD;
        ResponseEntity<String> response = doLoginRequest(password);
        context.setResponse(response);
    }

    private ResponseEntity<String> doCreateRequest() {
        User user = UserObjectMother.basic();

        final HttpRequest<User> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/users/register", user);
        return httpClient.doRequest(httpRequest);

    }

    private ResponseEntity<String> doLoginRequest(String password) {
        LoginRequest request = LoginRequestObjectMother.withPassword(password);

        final HttpRequest<LoginRequest> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/users/login", request);
        return httpClient.doRequest(httpRequest);
    }
}
