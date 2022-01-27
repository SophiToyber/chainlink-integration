package wrapper.chainlink.api;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static wrapper.chainlink.test.utils.WithdrawUtil.EXPECTED_BLOCK_NUMBER;
import static wrapper.chainlink.test.utils.WithdrawUtil.WITHDRAW_URL;
import static wrapper.chainlink.test.utils.WithdrawUtil.getDefaultTransactionResponse;
import static wrapper.chainlink.test.utils.WithdrawUtil.getDefaultWithdrawRequestDto;
import static wrapper.chainlink.test.utils.WithdrawUtil.TRANSACTION_HASH;
import static wrapper.chainlink.test.utils.WithdrawUtil.FROM;
import static wrapper.chainlink.test.utils.WithdrawUtil.TO;
import static wrapper.chainlink.test.utils.WithdrawUtil.AMOUNT;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import wrapper.chainlink.service.uri.ChainlinkUriBuilder;

@RunWith(SpringRunner.class)
public class WithdrawControllerTest extends BasicApiTest {

    @Autowired
    private ChainlinkUriBuilder chainlinkUriBuilder;
    
    @Before
    public void setup() {
        super.setUp();
    }

    @Test
    public void shouldGetChainlinkWithdrawBeEqualsToResponseEntityOk() throws Exception {
        mockResponseWithSuccess(chainlinkServiceServer, POST, chainlinkUriBuilder.getWithdrawUri().toString(),
                                getDefaultTransactionResponse());

        performPostInteraction(WITHDRAW_URL, getDefaultWithdrawRequestDto()).andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.transactionHash").value(TRANSACTION_HASH))
            .andExpect(jsonPath("$.to").value(TO))
            .andExpect(jsonPath("$.from").value(FROM))
            .andExpect(jsonPath("$.amount").value(AMOUNT))
            .andExpect(jsonPath("$.blockNumber").value(EXPECTED_BLOCK_NUMBER));

    }
}
