package wrapper.chainlink.exeption.integration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import io.micrometer.core.instrument.util.IOUtils;
import wrapper.chainlink.exeption.InnerServiceException;
import wrapper.chainlink.exeption.dto.ErrorInfoDto;

public abstract class BasicResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final String PROVIDER_ERROR_MESSAGE_TEMPLATE = "Server error [%s]: %s ";

    abstract String getServiceName();

    abstract void handleError(ErrorInfoDto errorInfoDto);

    private MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus status = response.getStatusCode();
        Optional<ErrorInfoDto> errorInfoDtoOptional = fetchErrorInfoDto(response);
        errorInfoDtoOptional.ifPresent(errorInfoDto -> {
            if (!CollectionUtils.isEmpty(errorInfoDto.getMessages())) {
                handleError(errorInfoDto);
            }
        });

        throwProviderException(response, status);
    }

    private void throwProviderException(ClientHttpResponse response, HttpStatus status) throws IOException {
        String errorMsg = getErrorMessage(response, status);
        throw new InnerServiceException(String.format(PROVIDER_ERROR_MESSAGE_TEMPLATE, getServiceName(), errorMsg));
    }

    private String getErrorMessage(ClientHttpResponse response, HttpStatus status) throws IOException {
        return status + " " + IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);
    }

    private Optional<ErrorInfoDto> fetchErrorInfoDto(ClientHttpResponse response) {
        try {
            return Optional.of((ErrorInfoDto) converter.read(ErrorInfoDto.class, response));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
