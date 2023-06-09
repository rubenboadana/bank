package com.iobuilders.wallet.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.wallet.domain.WalletObjectMother;
import com.iobuilders.wallet.domain.WalletService;
import com.iobuilders.wallet.domain.dto.Wallet;
import com.iobuilders.wallet.domain.dto.WalletID;
import com.iobuilders.wallet.infrastructure.controller.CreateWalletController;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CreateWalletController.class)
@AutoConfigureMockMvc
class CreateWalletControllerTest {

    private static final Long NEW_WALLET_ID = 1L;

    @MockBean
    private WalletService walletServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void should_responseInternalError_when_internalErrorIsProduced() throws Exception {
        //Given
        doThrow(new RuntimeException()).when(walletServiceMock).create(any(Wallet.class));

        //When/Then
        Wallet wallet = WalletObjectMother.basic();
        mockMvc.perform(post("/wallets").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(wallet)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void should_returnHTTP200_when_orderCreationSucceed() throws Exception {
        //Given
        doReturn(new WalletID(NEW_WALLET_ID)).when(walletServiceMock).create(any(Wallet.class));

        //When/Then
        Wallet wallet = WalletObjectMother.basic();
        mockMvc.perform(post("/wallets").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(wallet)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(NEW_WALLET_ID.intValue())));
    }

}
