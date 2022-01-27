package wrapper.chainlink.api;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wrapper.chainlink.test.utils.BalanceTestUtil.BALANCE_URL;
import static wrapper.chainlink.test.utils.BalanceTestUtil.EXPECTED_BALANCE;
import static wrapper.chainlink.test.utils.BalanceTestUtil.getDefaulChainlinkAccountBalanceResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import wrapper.chainlink.service.uri.ChainlinkUriBuilder;

@RunWith(SpringRunner.class)
public class BalanceControllerTest extends BasicApiTest {

    @Autowired
    private ChainlinkUriBuilder chainlinkUriBuilder;
    
    @Before
    public void setup() {
        super.setUp();
    }

    @Test
    public void shouldChainlinkCreateAccountStatusIsOk() throws Exception {

        mockResponseWithSuccess(chainlinkServiceServer, GET, chainlinkUriBuilder.getBalanseUri().toString(),
                                getDefaulChainlinkAccountBalanceResult());

        performGetInteraction(BALANCE_URL).andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.amount").value(EXPECTED_BALANCE));

    }

}
