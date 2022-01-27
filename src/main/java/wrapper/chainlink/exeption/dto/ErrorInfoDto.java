package wrapper.chainlink.exeption.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfoDto implements Serializable {

    private static final long serialVersionUID = 438647918717639850L;

    @NotNull
    private Integer status;
    @NotNull
    private List<String> messages;
    @NotNull
    private String debugMessage;
    @NotNull
    private Integer code;
    private String header;

}
