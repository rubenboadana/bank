package com.iobuilders.domain.bus.query;

import java.util.concurrent.ExecutionException;

public interface QueryBus {
    Response get(Query query) throws InterruptedException, ExecutionException;
}
