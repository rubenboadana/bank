package com.iobuilders.infrastructure;

import com.iobuilders.domain.UserService;
import com.iobuilders.domain.exceptions.UserNotFoundException;
import com.iobuilders.infrastructure.controller.DeleteUserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DeleteUserController.class)
@AutoConfigureMockMvc
class DeleteUserControllerTest {

    private static final String USER_ID = "26929514-237c-11ed-861d-0242ac120002";

    @MockBean
    private UserService userServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_responseUserNotFound_when_UserNotExist() throws Exception {
        //Given
        doThrow(new UserNotFoundException(USER_ID)).when(userServiceMock).delete(USER_ID);

        //When/Then
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Could not find the requested resource: User with id 1")));

    }

    @Test
    void should_responseInternalError_when_internalErrorIsProduced() throws Exception {
        //Given
        doThrow(new RuntimeException()).when(userServiceMock).delete(USER_ID);

        //When/Then
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void should_returnHTTP200_when_productDeletionSucceed() throws Exception {
        //Given
        doNothing().when(userServiceMock).delete(USER_ID);

        //When/Then
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

}
