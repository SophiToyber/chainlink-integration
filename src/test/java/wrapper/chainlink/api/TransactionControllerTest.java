package wrapper.chainlink.api;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static wrapper.chainlink.test.utils.TransactionUtil.EXPECTED_BLOCK_NUMBER;
import static wrapper.chainlink.test.utils.TransactionUtil.FROM;
import static wrapper.chainlink.test.utils.TransactionUtil.TO;
import static wrapper.chainlink.test.utils.TransactionUtil.EXPECTED_AMOUNT;
import static wrapper.chainlink.test.utils.TransactionUtil.TRANSACTION_HASH;
import static wrapper.chainlink.test.utils.TransactionUtil.TRANSACTION_URL;
import static wrapper.chainlink.test.utils.TransactionUtil.getDefaultTransactionResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import wrapper.chainlink.service.uri.ChainlinkUriBuilder;

@RunWith(SpringRunner.class)
public class TransactionControllerTest extends BasicApiTest {

    @Autowired
    private ChainlinkUriBuilder chainlinkUriBuilder;
    
    @Before
    public void setup() {
        super.setUp();
    }

    @Test
    public void shouldChainlinkGetTransactionStatusIsOk() throws Exception {

        mockResponseWithSuccess(chainlinkServiceServer, GET,
                                chainlinkUriBuilder.getTransactionUri(TRANSACTION_HASH).toString(),
                                getDefaultTransactionResponse());

        performGetInteraction(TRANSACTION_URL).andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.transactionHash").value(TRANSACTION_HASH))
            .andExpect(jsonPath("$.to").value(TO))
            .andExpect(jsonPath("$.from").value(FROM))
            .andExpect(jsonPath("$.amount").value(EXPECTED_AMOUNT))
            .andExpect(jsonPath("$.blockNumber").value(EXPECTED_BLOCK_NUMBER));

    }

}
