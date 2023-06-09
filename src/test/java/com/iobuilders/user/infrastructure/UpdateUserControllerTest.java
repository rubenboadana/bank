package com.iobuilders.user.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.user.domain.UserObjectMother;
import com.iobuilders.user.domain.UserService;
import com.iobuilders.user.domain.dto.User;
import com.iobuilders.user.domain.exceptions.UserNotFoundException;
import com.iobuilders.user.infrastructure.controller.UpdateUserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UpdateUserController.class)
@AutoConfigureMockMvc
class UpdateUserControllerTest {

    private static final Long PRODUCT_ID = 1L;
    private static final String NEW_USER_NAME = "Rb";

    @MockBean
    private UserService userServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void should_responseUserNotFound_when_invalidUserIdProvided() throws Exception {
        //Given
        doThrow(new UserNotFoundException(PRODUCT_ID)).when(userServiceMock).update(any(), any(User.class));

        //When/Then
        User product = UserObjectMother.basic();
        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Could not find the requested resource: User with id 1")));

    }


    @Test
    void should_responseInternalError_when_internalErrorIsProduced() throws Exception {
        //Given
        doThrow(new RuntimeException()).when(userServiceMock).update(any(), any(User.class));

        //When/Then
        User product = UserObjectMother.basic();
        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void should_returnHTTP200_when_updateProductSucceed() throws Exception {
        //Given
        User expectedProduct = UserObjectMother.withName(NEW_USER_NAME);
        doReturn(expectedProduct).when(userServiceMock).update(any(), any(User.class));

        //When/Then
        User product = UserObjectMother.basic();
        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
