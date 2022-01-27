package wrapper.chainlink.exeption.integration;

import org.springframework.util.StringUtils;

import wrapper.chainlink.exeption.dto.ErrorInfoDto;

public class RemoteMsPassThroughException extends RuntimeException {

    private static final long serialVersionUID = -1556350805101181933L;

    private final ErrorInfoDto errorInfoDto;

    RemoteMsPassThroughException(ErrorInfoDto errorInfoDto) {
        super(StringUtils.isEmpty(errorInfoDto.getMessages()) ? "Unknown remote MS error." : errorInfoDto.getMessages().get(0));
        this.errorInfoDto = errorInfoDto;
    }

    public ErrorInfoDto getErrorInfoDto() {
        return errorInfoDto;
    }

}
