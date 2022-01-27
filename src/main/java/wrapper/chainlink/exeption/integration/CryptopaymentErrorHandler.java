package wrapper.chainlink.exeption.integration;

import wrapper.chainlink.exeption.constants.ServiceName;
import wrapper.chainlink.exeption.dto.ErrorInfoDto;

public class CryptopaymentErrorHandler extends BasicResponseErrorHandler {

    @Override
    String getServiceName() {
        return ServiceName.CRYPTOPAYMENT.getName();
    }

    @Override
    void handleError(ErrorInfoDto errorInfoDto) {
        throw new RemoteMsPassThroughException(errorInfoDto);
    }
}
