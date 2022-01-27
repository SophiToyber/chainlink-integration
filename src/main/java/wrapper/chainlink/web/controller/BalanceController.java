package wrapper.chainlink.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.service.ChainlinkCommunicationService;
import wrapper.chainlink.web.models.responce.BalanceResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chainlink")
public class BalanceController {

    private final ChainlinkCommunicationService chainlinkService;

    @GetMapping("/balance")
    public BalanceResponseDto balance() {
        log.info("Request on get LINK balance");
        BalanceResponseDto balanceResponseDto = chainlinkService.getWalletBalance();
        log.info("Successful request on get balance. Actual balance is: {}", balanceResponseDto.toString());
        return balanceResponseDto;
    }
}
