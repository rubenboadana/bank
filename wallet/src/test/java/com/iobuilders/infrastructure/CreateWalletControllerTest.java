package com.iobuilders.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.domain.WalletObjectMother;
import com.iobuilders.domain.bus.command.CommandBus;
import com.iobuilders.domain.command.CreateWalletCommand;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.infrastructure.controller.CreateWalletController;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CreateWalletController.class)
@AutoConfigureMockMvc(addFilters = false)
class CreateWalletControllerTest {

    private static final String NEW_WALLET_ID = "26929514-237c-11ed-861d-0242ac120002";

    @MockBean
    private CommandBus commandBusMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_responseInternalError_when_CommandHandlerFailureIsProduced() throws Exception {
        //Given
        doThrow(new CommandExecutionException("Command handler thrown an exception", new RuntimeException())).when(commandBusMock).send(any(CreateWalletCommand.class));

        //When/Then
        Wallet wallet = WalletObjectMother.basic();
        mockMvc.perform(post("/wallets").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(wallet)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void should_returnHTTP200_when_orderCreationSucceed() throws Exception {
        //Given
        CompletableFuture completedFuture = CompletableFuture.completedFuture(null);
        doReturn(completedFuture).when(commandBusMock).send(any(CreateWalletCommand.class));
        Wallet wallet = WalletObjectMother.basic();

        SecurityContextHolder.setContext(
                SecurityContextHolder.createEmptyContext()
        );

        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken("rubenboada", null)
        );

        //When/Then

        MvcResult mvcResult = this.mockMvc.perform(post("/wallets").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(wallet)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated());
    }

}
