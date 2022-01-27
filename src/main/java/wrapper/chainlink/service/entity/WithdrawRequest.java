package wrapper.chainlink.service.entity;

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
public class WithdrawRequest {
    @NotNull
    private String from;
    @NotNull
    private String privateKey;
    @NotNull
    private String to;
    @NotNull
    private Double quantity;
}
