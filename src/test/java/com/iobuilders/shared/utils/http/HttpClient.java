package com.iobuilders.shared.utils.http;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class HttpClient {
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    public <T> ResponseEntity<String> doRequest(HttpRequest<T> httpRequest) {
        try {
            URL url = new URL(PROTOCOL, HOST, port, httpRequest.getPath());
            final HttpEntity httpEntity = new HttpEntity<>(httpRequest.getBody(), getHeaders());
            final HttpMethod httpMethod = Optional.ofNullable(HttpMethod.valueOf(httpRequest.getMethod())).orElseThrow(IllegalArgumentException::new);
            return getResponseEntityFor(url, httpEntity, httpMethod);
        } catch (IOException exception) {
            throw new RestClientException(exception.getMessage(), exception);
        }
    }

    public static <T> HttpRequest<T> createHttpRequest(final com.iobuilders.shared.utils.http.HttpMethod httpMethod, final String requestURL, final T requestBody) {
        final HttpRequest.Builder<T> httpRequestBuilder = new HttpRequest.Builder<>(httpMethod, requestURL);
        if (httpMethod.hasMandatoryBody()) {
            return httpRequestBuilder.withBody(requestBody).build();
        }
        return httpRequestBuilder.build();
    }

    private HttpHeaders getHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private ResponseEntity<String> getResponseEntityFor(URL url, HttpEntity<?> httpEntity, HttpMethod httpMethod) {
        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(url.toString(), httpMethod, httpEntity, String.class);
        } catch (HttpStatusCodeException e) {
            response = ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

        return response;
    }

}
