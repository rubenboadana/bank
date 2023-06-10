package com.iobuilders.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.domain.WalletObjectMother;
import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.exceptions.WalletNotFoundException;
import com.iobuilders.infrastructure.controller.UpdateWalletController;
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

@WebMvcTest(controllers = UpdateWalletController.class)
@AutoConfigureMockMvc(addFilters = false)
class UpdateWalletControllerTest {

    private static final String WALLET_ID = "26929514-237c-11ed-861d-0242ac120002";
    private static final double NEW_WALLET_QUANTITY = 120.0;

    @MockBean
    private WalletService walletServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_responseWalletNotFound_when_invalidWalletIdProvided() throws Exception {
        //Given
        doThrow(new WalletNotFoundException(WALLET_ID)).when(walletServiceMock).deposit(any(), any(Quantity.class));

        //When/Then
        Wallet wallet = WalletObjectMother.basic();
        mockMvc.perform(put("/wallets/" + WALLET_ID).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(wallet)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Could not find the requested resource: Wallet with id " + WALLET_ID)));

    }


    @Test
    void should_responseInternalError_when_internalErrorIsProduced() throws Exception {
        //Given
        doThrow(new RuntimeException()).when(walletServiceMock).deposit(any(), any(Quantity.class));

        //When/Then
        Wallet wallet = WalletObjectMother.basic();
        mockMvc.perform(put("/wallets/" + WALLET_ID).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(wallet)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void should_returnHTTP200_when_depositIntoWalletSucceed() throws Exception {
        //Given
        Wallet expectedWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(expectedWallet).when(walletServiceMock).deposit(any(), any(Quantity.class));

        //When/Then
        mockMvc.perform(put("/wallets/" + WALLET_ID).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new Quantity(NEW_WALLET_QUANTITY))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quantity", is(NEW_WALLET_QUANTITY)));
    }

}
