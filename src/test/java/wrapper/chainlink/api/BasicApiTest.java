package wrapper.chainlink.api;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public abstract class BasicApiTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected RestTemplate cryptoPaymentRestTemplate;
    @Autowired
    protected RestTemplate chainlinkApiRestTemplate;

    private MockMvc mockMvc;

    protected MockRestServiceServer chainlinkServiceServer;

    protected MockRestServiceServer cryptoPaymentServiceServer;

    protected void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.cryptoPaymentServiceServer = MockRestServiceServer.bindTo(cryptoPaymentRestTemplate).build();
        this.chainlinkServiceServer = MockRestServiceServer.bindTo(chainlinkApiRestTemplate).bufferContent().build();
    }

    protected ResultActions performGetInteraction(String uri) throws Exception {
        return this.mockMvc.perform(get(uri).contentType(APPLICATION_JSON).accept(APPLICATION_JSON));
    }

    protected ResultActions performPostInteraction(String uri, Object dataRequest) throws Exception {
        return this.mockMvc.perform(post(uri).contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dataRequest))
            .accept(APPLICATION_JSON));
    }

    protected void mockGetResponseWithHttpStatusOk(MockRestServiceServer mockRestServiceServer, String uri) {
        mockRestServiceServer.expect(ExpectedCount.manyTimes(), requestTo(uri))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK));
    }

    protected void mockResponseWithSuccess(MockRestServiceServer mockRestServiceServer, HttpMethod httpMethod,
            String uri, Object dataResponse) throws Exception {
        String stringifiedDataResponse = objectMapper.writeValueAsString(dataResponse);
        mockRestServiceServer.expect(ExpectedCount.manyTimes(), requestTo(uri))
            .andExpect(method(httpMethod))
            .andRespond(withSuccess(stringifiedDataResponse, APPLICATION_JSON));
    }
    
}
