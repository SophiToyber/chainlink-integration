package wrapper.chainlink.web.controller;

import static wrapper.chainlink.service.transformer.WithdrawTransformer.transform;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.service.ChainlinkCommunicationService;
import wrapper.chainlink.web.models.request.WithdrawRequestDto;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/wrapper/withdraw")
@RestController
public class WithdrawController {

    private final ChainlinkCommunicationService chainlinkService;

    @PostMapping
    public TransactionResponseDto withdraw(@NotNull @Valid @RequestBody WithdrawRequestDto withdrawRequestDto) {
        log.info("Request on withdraw LINK coin. Witdraw request: {}", withdrawRequestDto.toString());
        return chainlinkService.withdraw(transform(withdrawRequestDto));
    }
}
