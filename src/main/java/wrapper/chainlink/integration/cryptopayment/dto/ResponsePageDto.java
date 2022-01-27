package wrapper.chainlink.integration.cryptopayment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePageDto<T> {

    private List<T> content;
    private long totalElements;
    private int totalPages;

}
