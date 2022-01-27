package wrapper.chainlink.web.controller;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.service.ChainlinkCommunicationService;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wrapper/transaction")
public class TransactionController {

    private final ChainlinkCommunicationService chainlinkService;

    @GetMapping
    public TransactionResponseDto transaction(@NotNull @RequestParam("hash") String hash) {
        log.info("Request on get transaction by hash: {}", hash);
        return chainlinkService.getWalletTransaction(hash);
    }

}
