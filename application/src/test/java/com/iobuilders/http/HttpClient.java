package com.iobuilders.http;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

    public static <T> HttpRequest<T> createHttpRequest(final com.iobuilders.http.HttpMethod httpMethod, final String requestURL, final T requestBody) {
        final HttpRequest.Builder<T> httpRequestBuilder = new HttpRequest.Builder<>(httpMethod, requestURL);
        if (httpMethod.hasMandatoryBody()) {
            return httpRequestBuilder.withBody(requestBody).build();
        }
        return httpRequestBuilder.build();
    }

    public <T> ResponseEntity<String> doRequest(HttpRequest<T> httpRequest) {
        try {
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            URL url = new URL(PROTOCOL, HOST, port, httpRequest.getPath());
            final HttpEntity httpEntity = new HttpEntity<>(httpRequest.getBody(), getHeaders());
            final HttpMethod httpMethod = Optional.ofNullable(HttpMethod.resolve(httpRequest.getMethod())).orElseThrow(IllegalArgumentException::new);
            return getResponseEntityFor(url, httpEntity, httpMethod);
        } catch (IOException exception) {
            throw new RestClientException(exception.getMessage(), exception);
        }
    }

    public <T> ResponseEntity<String> doAuthenticatedRequest(HttpRequest<T> httpRequest, String token) {
        try {
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            URL url = new URL(PROTOCOL, HOST, port, httpRequest.getPath());
            HttpHeaders httpHeaders = getHeaders();
            httpHeaders.setBearerAuth(token);
            final HttpEntity httpEntity = new HttpEntity<>(httpRequest.getBody(), httpHeaders);
            final HttpMethod httpMethod = Optional.ofNullable(HttpMethod.resolve(httpRequest.getMethod())).orElseThrow(IllegalArgumentException::new);
            return getResponseEntityFor(url, httpEntity, httpMethod);
        } catch (IOException exception) {
            throw new RestClientException(exception.getMessage(), exception);
        }
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
            response = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

        return response;
    }

}
