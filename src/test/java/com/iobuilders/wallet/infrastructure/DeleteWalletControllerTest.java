package com.iobuilders.wallet.infrastructure;

import com.iobuilders.wallet.domain.WalletService;
import com.iobuilders.wallet.domain.exceptions.WalletNotFoundException;
import com.iobuilders.wallet.infrastructure.controller.DeleteWalletController;
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

@WebMvcTest(controllers = DeleteWalletController.class)
@AutoConfigureMockMvc
class DeleteWalletControllerTest {

    private static final Long WALLET_ID = 1L;

    @MockBean
    private WalletService walletServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_responseWalletNotFound_when_walletNotExist() throws Exception {
        //Given
        doThrow(new WalletNotFoundException(WALLET_ID)).when(walletServiceMock).delete(WALLET_ID);

        //When/Then
        mockMvc.perform(delete("/wallets/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Could not find wallet with id 1")));

    }

    @Test
    void should_responseInternalError_when_internalErrorIsProduced() throws Exception {
        //Given
        doThrow(new RuntimeException()).when(walletServiceMock).delete(WALLET_ID);

        //When/Then
        mockMvc.perform(delete("/wallets/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void should_returnHTTP200_when_walletDeletionSucceed() throws Exception {
        //Given
        doNothing().when(walletServiceMock).delete(WALLET_ID);

        //When/Then
        mockMvc.perform(delete("/wallets/1"))
                .andExpect(status().isOk());
    }

}
