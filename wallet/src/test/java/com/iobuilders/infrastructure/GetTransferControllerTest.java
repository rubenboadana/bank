package com.iobuilders.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.domain.WalletObjectMother;
import com.iobuilders.domain.WalletOverviewObjectMother;
import com.iobuilders.domain.bus.query.QueryBus;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.query.FindWalletTransactionsQuery;
import com.iobuilders.infrastructure.controller.GetTransferController;
import org.axonframework.queryhandling.QueryExecutionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GetTransferController.class)
@AutoConfigureMockMvc(addFilters = false)
class GetTransferControllerTest {
    public static final String WALLET_ID = "b389d364-07ed-11ee-be56-0242ac120003";
    private static final String USERNAME = "rubenboada";
    @MockBean
    private QueryBus queryBusMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_responseNotFound_when_QueryHandlerFailureIsProduced() throws Exception {
        //Given
        doThrow(new ExecutionException(new QueryExecutionException("Query handler thrown an exception", new RuntimeException()))).when(queryBusMock).get(any(FindWalletTransactionsQuery.class));

        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(USERNAME, null));

        //When/Then
        Wallet wallet = WalletObjectMother.basic();
        mockMvc.perform(get("/wallets/" + WALLET_ID + "/transactions"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_returnHTTP200_when_orderCreationSucceed() throws Exception {
        //Given
        WalletOverview walletOverview = WalletOverviewObjectMother.basic();
        doReturn(walletOverview).when(queryBusMock).get(any(FindWalletTransactionsQuery.class));

        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(USERNAME, null));

        //When/Then
        this.mockMvc.perform(get("/wallets/" + WALLET_ID + "/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quantity", is(walletOverview.getQuantity())));

    }

}
