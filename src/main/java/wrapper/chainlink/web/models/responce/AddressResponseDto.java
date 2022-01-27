package wrapper.chainlink.web.models.responce;

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
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressResponseDto {

    @NotNull
    private String address;

    @NotNull
    private String privateKey;

    @Override
    public String toString() {
        return String.format("{address = %s}", address);
    }

}
