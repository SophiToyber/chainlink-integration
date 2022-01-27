package wrapper.chainlink.web.models.responce;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionResponseDto {
    @NotNull
    private String transactionHash;
    @NotNull
    private String to;
    @NotNull
    private String from;
    @NotNull
    private Double amount;
    @NotNull
    private Integer blockNumber;

}
