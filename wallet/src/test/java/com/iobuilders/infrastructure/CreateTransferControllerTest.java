package com.iobuilders.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.domain.WalletTransactionObjectMother;
import com.iobuilders.domain.bus.command.CommandBus;
import com.iobuilders.domain.command.TransferMoneyCommand;
import com.iobuilders.domain.dto.WalletTransaction;
import com.iobuilders.infrastructure.controller.CreateTransferController;
import org.axonframework.commandhandling.CommandExecutionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CreateTransferController.class)
@AutoConfigureMockMvc(addFilters = false)
class CreateTransferControllerTest {
    public static final String USERNAME = "rubenboada";
    public static final String WALLET_ID = "b389d364-07ed-11ee-be56-0242ac120003";


    @MockBean
    private CommandBus commandBusMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_responseInternalError_when_CommandHandlerFailureIsProduced() throws Exception {
        //Given
        doThrow(new CommandExecutionException("Command handler thrown an exception", new RuntimeException())).when(commandBusMock).send(any(TransferMoneyCommand.class));
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transference();

        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(USERNAME, null));

        //When/Then
        mockMvc.perform(post("/wallets/" + WALLET_ID + "/transactions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(walletTransaction)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_returnHTTP200_when_transferenceCreationSucceed() throws Exception {
        //Given
        CompletableFuture completedFuture = CompletableFuture.completedFuture(null);
        doReturn(completedFuture).when(commandBusMock).send(any(TransferMoneyCommand.class));
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transference();


        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(USERNAME, null));

        //When/Then
        MvcResult mvcResult = this.mockMvc.perform(post("/wallets/" + WALLET_ID + "/transactions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(walletTransaction)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated());
    }

}
