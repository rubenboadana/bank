package com.iobuilders.steps;

import com.iobuilders.ScenarioContext;
import com.iobuilders.domain.dto.*;
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
    public static final String DEFAULT_WALLET_ID = "26929514-237c-11ed-861d-0242ac120001";
    public static final String DEFAULT_WALLET_OWNER_ID = "26929514-237c-11ed-861d-0242ac120002";
    public static final String DEFAULT_EXTERNAL_WALLET_ID = "26929514-237c-11ed-861d-0242ac120003";
    public static final String DEFAULT_EXTERNAL_WALLET_OWNER_ID = "26929514-237c-11ed-861d-0242ac120004";
    public static final double DEFAULT_WALLET_QUANTITY = 10;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ScenarioContext context;

    private static void timeToSyncWalletAndUser() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Given("^a valid wallet is available$")
    public void walletIsAlreadyCreated() throws InterruptedException {
        ResponseEntity<String> response = doCreateRequest(DEFAULT_WALLET_ID, DEFAULT_WALLET_OWNER_ID);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        timeToSyncWalletAndUser();
    }

    @When("^valid wallet creation request is sent$")
    public void walletCreationIsRequested() {
        ResponseEntity<String> response = doCreateRequest(DEFAULT_WALLET_ID, DEFAULT_WALLET_OWNER_ID);
        context.setResponse(response);
    }

    @When("^user deposit money into it$")
    public void depositMoney() {
        ResponseEntity<String> response = doPutRequest();
        context.setResponse(response);
    }

    @When("^user transfers money into it$")
    public void transferMoney() {
        ResponseEntity<String> response = doTransferenceRequest();
        context.setResponse(response);
    }

    @When("^user requests wallet transactions with pagination \"([^\"]*)\"$")
    public void listTransactions(String pagination) {
        ResponseEntity<String> response = doGetRequest(pagination);
        context.setResponse(response);
    }

    private ResponseEntity<String> doGetRequest(String pagination) {
        final HttpRequest<String> httpRequest = HttpClient.createHttpRequest(HttpMethod.GET, "/wallets/" + DEFAULT_WALLET_ID + "/transactions?" + pagination, "");
        return httpClient.doAuthenticatedRequest(httpRequest, context.getToken());
    }


    private ResponseEntity<String> doCreateRequest(String walletId, String ownerId) {
        Wallet wallet = Wallet.builder()
                .id(walletId)
                .owner(new WalletOwner(ownerId))
                .quantity(new Quantity(DEFAULT_WALLET_QUANTITY))
                .build();

        final HttpRequest<Wallet> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/wallets", wallet);
        return httpClient.doAuthenticatedRequest(httpRequest, context.getToken());
    }

    private ResponseEntity<String> doPutRequest() {
        Quantity quantity = new Quantity(DEFAULT_WALLET_QUANTITY);

        final HttpRequest<Quantity> httpRequest = HttpClient.createHttpRequest(HttpMethod.PUT, "/wallets/" + DEFAULT_WALLET_ID, quantity);
        return httpClient.doAuthenticatedRequest(httpRequest, context.getToken());
    }

    @Given("^a second valid wallet is available$")
    public void externalWalletIsAlreadyCreated() throws InterruptedException {
        ResponseEntity<String> response = doCreateRequest(DEFAULT_EXTERNAL_WALLET_ID, DEFAULT_EXTERNAL_WALLET_OWNER_ID);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        timeToSyncWalletAndUser();
    }

    private ResponseEntity<String> doTransferenceRequest() {
        WalletTransaction walletTransaction = WalletTransaction.builder().createdBy(RegisterRequestObjectMother.DEFAULT_USERNAME).originWalletId(DEFAULT_WALLET_ID).destinyWalletId(DEFAULT_EXTERNAL_WALLET_ID).quantity(DEFAULT_WALLET_QUANTITY).build();

        final HttpRequest<WalletTransaction> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/wallets/" + DEFAULT_WALLET_ID + "/transactions", walletTransaction);
        return httpClient.doAuthenticatedRequest(httpRequest, context.getToken());
    }
}
