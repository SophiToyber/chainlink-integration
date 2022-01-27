package wrapper.chainlink.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.service.ChainlinkCommunicationService;
import wrapper.chainlink.web.models.responce.AddressResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chainlink")
public class AddressController {

    private final ChainlinkCommunicationService chainlinkService;

    @GetMapping("/address")
    public AddressResponseDto address() {
        log.info("Request on get address controller");
        AddressResponseDto addressResponseDto = chainlinkService.getWalletAddress();
        log.info("Successful request on get address. Address response is: {}", addressResponseDto.toString());
        return addressResponseDto;
    }

}
