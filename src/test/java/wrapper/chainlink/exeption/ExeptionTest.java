package wrapper.chainlink.exeption;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static wrapper.chainlink.test.utils.ExceptionUtil.EXPECTED_ILLEGAL_ARGUMENT_EX;
import static wrapper.chainlink.test.utils.ExceptionUtil.FALSE_STRING_OBJECT;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.api.BasicApiTest;
import wrapper.chainlink.web.models.responce.BalanceResponseDto;

@Slf4j
@RunWith(SpringRunner.class)
public class ExeptionTest extends BasicApiTest {

    @Before
    public void setup() {
        super.setUp();
    }

    @Test
    public void shouldIllegalArgumentExceptionThrowExpectedExeption() {
        RestTemplate restTemplate = new RestTemplate();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BalanceResponseDto balanceResponse = restTemplate.exchange(FALSE_STRING_OBJECT, GET, EMPTY, BalanceResponseDto.class)
                .getBody();
        });

        assertTrue(exception.getMessage().contains(EXPECTED_ILLEGAL_ARGUMENT_EX));
        log.info(exception.getMessage());
    }
}
