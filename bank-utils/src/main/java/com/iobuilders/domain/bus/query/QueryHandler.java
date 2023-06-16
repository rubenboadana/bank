package com.iobuilders.domain.bus.query;

import java.util.concurrent.ExecutionException;

public interface QueryHandler<T extends Query, R extends Response> {
    R handle(T query) throws InterruptedException, ExecutionException;
}
