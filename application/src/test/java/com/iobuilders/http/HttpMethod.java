package com.iobuilders.http;

public enum HttpMethod {
    GET(org.springframework.http.HttpMethod.GET.name(), false),
    POST(org.springframework.http.HttpMethod.POST.name(), true),
    PUT(org.springframework.http.HttpMethod.PUT.name(), true),
    DELETE(org.springframework.http.HttpMethod.DELETE.name(), false);

    private final String name;
    private final boolean requiresBody;

    HttpMethod(String name, boolean requiresBody) {
        this.name = name;
        this.requiresBody = requiresBody;
    }

    public boolean hasMandatoryBody() {
        return requiresBody;
    }
}
