package com.iobuilders.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.application.handler.CreateUserCommand;
import com.iobuilders.domain.UserObjectMother;
import com.iobuilders.domain.bus.command.CommandBus;
import com.iobuilders.domain.dto.User;
import com.iobuilders.infrastructure.controller.CreateUserController;
import org.axonframework.commandhandling.CommandExecutionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CreateUserController.class)
@AutoConfigureMockMvc
class CreateUserControllerTest {

    @MockBean
    private CommandBus commandBusMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_responseInternalError_when_internalErrorIsProduced() throws Exception {
        //Given
        doThrow(new CommandExecutionException("Command handler thrown an exception", new RuntimeException())).when(commandBusMock).send(any(CreateUserCommand.class));

        //When/Then
        User user = UserObjectMother.basic();
        mockMvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void should_returnHTTP200_when_orderCreationSucceed() throws Exception {
        //Given
        doReturn(new CompletableFuture<Void>()).when(commandBusMock).send(any(CreateUserCommand.class));

        //When/Then
        User user = UserObjectMother.basic();
        mockMvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

}
