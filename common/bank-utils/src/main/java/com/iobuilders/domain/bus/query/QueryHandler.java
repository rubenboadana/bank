package com.iobuilders.domain.bus.query;

public interface QueryHandler<T extends Query, R extends Response> {
    R handle(T query) throws InterruptedException;
}
