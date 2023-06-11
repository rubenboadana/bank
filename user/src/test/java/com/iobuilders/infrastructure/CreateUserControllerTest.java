package com.iobuilders.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.domain.bus.command.CommandBus;
import com.iobuilders.domain.command.CreateUserCommand;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserObjectMother;
import com.iobuilders.infrastructure.controller.CreateUserController;
import org.axonframework.commandhandling.CommandExecutionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CreateUserController.class)
@AutoConfigureMockMvc(addFilters = false)
class CreateUserControllerTest {

    @MockBean
    private CommandBus commandBusMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void should_responseBadRequestError_when_CommandHandlerFailureIsProduced() throws Exception {
        //Given
        doThrow(new CommandExecutionException("Command handler thrown an exception", new RuntimeException())).when(commandBusMock).send(any(CreateUserCommand.class));

        //When/Then
        User user = UserObjectMother.basic();
        mockMvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void should_returnHTTP200_when_UserCreationSucceed() throws Exception {
        //Given
        CompletableFuture completedFuture = CompletableFuture.completedFuture(null);
        doReturn(completedFuture).when(commandBusMock).send(any(CreateUserCommand.class));
        User user = UserObjectMother.basic();

        //When/Then
        MvcResult mvcResult = this.mockMvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated());
    }

}
