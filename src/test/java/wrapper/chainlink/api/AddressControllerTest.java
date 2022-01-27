package wrapper.chainlink.api;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wrapper.chainlink.test.utils.AddressUtil.getDefaultAddressResponse;
import static wrapper.chainlink.test.utils.AddressUtil.EXPECTED_ADDRESS;
import static wrapper.chainlink.test.utils.AddressUtil.PRIVATE_KEY;
import static wrapper.chainlink.test.utils.AddressUtil.ADDRESS_URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import wrapper.chainlink.service.uri.ChainlinkUriBuilder;

@RunWith(SpringRunner.class)
public class AddressControllerTest extends BasicApiTest {

    @Autowired
    private ChainlinkUriBuilder chainlinkUriBuilder;
    
    @Before
    public void setup() {
        super.setUp();
    }

    @Test
    public void shouldChainlinkCreateAccountStatusIsOk() throws Exception {

        mockResponseWithSuccess(chainlinkServiceServer, GET, chainlinkUriBuilder.getAddressUri().toString(),
                                getDefaultAddressResponse());

        performGetInteraction(ADDRESS_URL).andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.address").value(EXPECTED_ADDRESS))
            .andExpect(jsonPath("$.privateKey").value(PRIVATE_KEY));
    }

}
