package wrapper.chainlink.integration.cryptopayment.dto;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDataDto {
    @NotNull
    private String walletAddress;
    @NotNull
    private String walletKey;
    @NotNull
    private Integer blockNumber;
}
