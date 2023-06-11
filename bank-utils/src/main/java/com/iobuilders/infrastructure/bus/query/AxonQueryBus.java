package com.iobuilders.infrastructure.bus.query;

import com.iobuilders.domain.bus.query.Query;
import com.iobuilders.domain.bus.query.QueryBus;
import com.iobuilders.domain.bus.query.Response;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class AxonQueryBus implements QueryBus {

    private final QueryGateway queryGateway;

    @Autowired
    public AxonQueryBus(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @Override
    public Response get(Query query) throws InterruptedException, ExecutionException {
        return queryGateway.query(query, Response.class).get();
    }
}
