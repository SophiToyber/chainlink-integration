package wrapper.chainlink.service.transformer;

import wrapper.chainlink.service.entity.WithdrawRequest;
import wrapper.chainlink.web.models.request.WithdrawRequestDto;

public class WithdrawTransformer {

    public static WithdrawRequest transform(WithdrawRequestDto withdrawRequestDto) {
        return WithdrawRequest.builder()
            .to(withdrawRequestDto.getTo())
            .quantity(withdrawRequestDto.getQuantity())
            .build();
    }

    public static WithdrawRequest transform(WithdrawRequest withdrawRequest, String from, String privateKey) {
        return WithdrawRequest.builder()
            .to(withdrawRequest.getTo())
            .quantity(withdrawRequest.getQuantity())
            .from(from)
            .privateKey(privateKey)
            .build();
    }

}