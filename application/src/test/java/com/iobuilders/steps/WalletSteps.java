package com.iobuilders.steps;

import com.iobuilders.ScenarioContext;
import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOwner;
import com.iobuilders.http.HttpClient;
import com.iobuilders.http.HttpMethod;
import com.iobuilders.http.HttpRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletSteps {
    private static final String VALID_WALLET = "valid";
    public static final String DEFAULT_WALLET_ID = "26929514-237c-11ed-861d-0242ac120001";
    public static final String DEFAULT_WALLET_OWNER_ID = "26929514-237c-11ed-861d-0242ac120002";
    public static final int DEFAULT_WALLET_QUANTITY = 10;
    private static final String NOT_EXISTING_WALLET_ID = "909090909090909090";
    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ScenarioContext context;

    @Given("a valid wallet is available")
    public void walletIsAlreadyCreated() {
        ResponseEntity<String> response = doCreateRequest();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @When("^valid wallet creation request is sent$")
    public void walletCreationIsRequested() {
        ResponseEntity<String> response = doCreateRequest();
        context.setResponse(response);
    }

    @When("^(valid|invalid) wallet delete request is sent$")
    public void walletDeleteIsRequested(String requestType) {
        String walletID = VALID_WALLET.equals(requestType) ? DEFAULT_WALLET_ID : NOT_EXISTING_WALLET_ID;
        ResponseEntity<String> response = doDeleteRequest(walletID);
        context.setResponse(response);
    }

    private ResponseEntity<String> doCreateRequest() {
        Wallet wallet = Wallet.builder()
                .id(DEFAULT_WALLET_ID)
                .owner(new WalletOwner(DEFAULT_WALLET_OWNER_ID))
                .quantity(new Quantity(DEFAULT_WALLET_QUANTITY))
                .build();

        final HttpRequest<Wallet> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/wallets", wallet);
        return httpClient.doAuthenticatedRequest(httpRequest, context.getToken());

    }

    private ResponseEntity<String> doDeleteRequest(String walletId) {
        Wallet wallet = Wallet.builder()
                .owner(new WalletOwner(DEFAULT_WALLET_OWNER_ID))
                .quantity(new Quantity(DEFAULT_WALLET_QUANTITY))
                .build();

        final HttpRequest<Wallet> httpRequest = HttpClient.createHttpRequest(HttpMethod.DELETE, "/wallets/" + walletId, wallet);
        return httpClient.doAuthenticatedRequest(httpRequest, context.getToken());
    }

}
