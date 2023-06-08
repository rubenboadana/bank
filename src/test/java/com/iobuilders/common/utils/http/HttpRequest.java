package com.iobuilders.common.utils.http;

import com.iobuilders.common.utils.http.exception.MissingHttpRequestParametersException;

public final class HttpRequest<T> {
    private final T body;
    private final String path;
    private final HttpMethod method;

    private HttpRequest(Builder<T> httpRequestBuilder) {
        if (httpRequestBuilder.method.hasMandatoryBody() && httpRequestBuilder.body == null) {
            throw new MissingHttpRequestParametersException();
        }

        this.body = httpRequestBuilder.body;
        this.path = httpRequestBuilder.path;
        this.method = httpRequestBuilder.method;
    }

    public T getBody() {
        return body;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method.name();
    }

    public static class Builder<T> {
        private T body;
        private final String path;
        private final HttpMethod method;

        public Builder(HttpMethod method, String path) {
            this.method = method;
            this.path = path;
        }

        public Builder<T> withBody(T body) {
            this.body = body;
            return this;
        }


        public HttpRequest<T> build() {
            return new HttpRequest<>(this);
        }

    }
}