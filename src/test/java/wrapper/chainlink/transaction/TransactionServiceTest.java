package wrapper.chainlink.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static wrapper.chainlink.test.utils.TransactionServiceUtil.EXPECTED_ADDRESS;
import static wrapper.chainlink.test.utils.TransactionServiceUtil.EXPECTED_PRIVATE_KEY;
import static wrapper.chainlink.test.utils.TransactionServiceUtil.getDefaultBalance;
import static wrapper.chainlink.test.utils.TransactionServiceUtil.getDefaultTransactionResponseDto;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.integration.cryptopayment.dto.AccountDataDto;
import wrapper.chainlink.integration.cryptopayment.dto.ResponsePageDto;
import wrapper.chainlink.integration.cryptopayment.service.CryptoPaymentCommunicationService;
import wrapper.chainlink.integration.cryptopayment.service.CryptoPaymentCommunicationServiceImpl;
import wrapper.chainlink.service.ChainlinkCommunicationService;
import wrapper.chainlink.service.entity.WithdrawRequest;
import wrapper.chainlink.service.properties.ChainlinkApiProperties;
import wrapper.chainlink.sheduler.TransactionSheduler;
import wrapper.chainlink.sheduler.service.TransactionShedulerService;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

@Slf4j
@RunWith(PowerMockRunner.class)
@ContextConfiguration(classes = TransactionSheduler.class)
public class TransactionServiceTest {

    private CryptoPaymentCommunicationService cryptoPaymentCommunicationService;
    private ChainlinkCommunicationService chainlinkCommunicationService;
    private ChainlinkApiProperties chainlinkBlockchainProperties;
    private TransactionShedulerService objectUnderTest;
    private TransactionSheduler transactionSheduler;

    @Captor
    private ArgumentCaptor<List<TransactionResponseDto>> transactionResponseCapture;
    @Captor
    private ArgumentCaptor<Integer> integerCapture;

    private ChainlinkApiProperties.Blockchain blockchain;
    private ChainlinkApiProperties.Settings settings;
    private ChainlinkApiProperties.Timeout timeout;
    private ChainlinkApiProperties.Url url;
    private ChainlinkApiProperties.Wallet wallet;

    private ObjectMapper mapper;
    private ResponsePageDto<AccountDataDto> accountsDataResponsePageFirstCall;
    private List<TransactionResponseDto> transactionResposeDto;

    @Before
    public void setUp() throws Exception {
        this.cryptoPaymentCommunicationService = mock(CryptoPaymentCommunicationServiceImpl.class);
        this.chainlinkCommunicationService = mock(ChainlinkCommunicationService.class);
        this.chainlinkBlockchainProperties = mock(ChainlinkApiProperties.class);
        
        this.objectUnderTest = new TransactionShedulerService(chainlinkCommunicationService,
                cryptoPaymentCommunicationService, chainlinkBlockchainProperties);

        ReflectionTestUtils.setField(objectUnderTest, "depositAccountDataPageSize", 3);

        this.blockchain = mock(ChainlinkApiProperties.Blockchain.class);
        this.settings = mock(ChainlinkApiProperties.Settings.class);
        this.timeout = mock(ChainlinkApiProperties.Timeout.class);
        this.url = mock(ChainlinkApiProperties.Url.class);
        this.wallet = mock(ChainlinkApiProperties.Wallet.class);
        this.mapper = new ObjectMapper();
        
        this.transactionSheduler = new TransactionSheduler(objectUnderTest, cryptoPaymentCommunicationService);
        
        prepareListsFromFiles();
    }

    @Test
    public void shouldTransactionShedulerServiceReturnExpectedValue() throws Exception {
        prepareChainlinkBlockchainProperties();

        MockitoAnnotations.initMocks(this);
        objectUnderTest.init();

        prepareCryptopaymentAccountsDataMock();

        when(chainlinkCommunicationService.getWalletBalance(any(String.class))).thenReturn(getDefaultBalance());
        when(chainlinkCommunicationService.withdrawETH(any(WithdrawRequest.class)))
            .thenReturn(getDefaultTransactionResponseDto());
        when(chainlinkCommunicationService.withdraw(any(WithdrawRequest.class)))
            .thenReturn(getDefaultTransactionResponseDto());
        when(chainlinkCommunicationService.getWalletTransactionsList(any(String.class), any(Integer.class)))
            .thenReturn(transactionResposeDto);
        
        List<TransactionResponseDto> transactionServiceResponse  = objectUnderTest.getChainlinkDepositTransactions();
        transactionSheduler.getNewChainlinkDepositTransactions();
        
        verify(cryptoPaymentCommunicationService)
            .sendNewDepositTransactionsToProcess(transactionResponseCapture.capture());
        assertThat(transactionServiceResponse.equals(transactionResponseCapture.getAllValues()));
        
        String expectedResponse = mapper.writeValueAsString(transactionResposeDto);
        String actualResponse = mapper.writeValueAsString(transactionServiceResponse);
        assertThat(expectedResponse.contains(actualResponse));
    }

    private void prepareChainlinkBlockchainProperties() {
        when(chainlinkBlockchainProperties.getBlockchain()).thenReturn(blockchain);
        when(blockchain.getMinAllowedBalanceSendTransaction()).thenReturn(0.2);
        when(blockchain.getFee()).thenReturn(0.0001);

        when(chainlinkBlockchainProperties.getWallet()).thenReturn(wallet);
        when(wallet.getAddress()).thenReturn(EXPECTED_ADDRESS);
        when(wallet.getPrivateKey()).thenReturn(EXPECTED_PRIVATE_KEY);

        when(chainlinkBlockchainProperties.getSettings()).thenReturn(settings);
        when(chainlinkBlockchainProperties.getTimeout()).thenReturn(timeout);
        when(chainlinkBlockchainProperties.getUrl()).thenReturn(url);
        when(settings.getThreadPoolSize()).thenReturn(16);

    }

    private void prepareCryptopaymentAccountsDataMock() {
        doReturn(accountsDataResponsePageFirstCall).when(cryptoPaymentCommunicationService)
            .getListOfAccountDepositData(eq(1), eq(3));
    }

    private void prepareListsFromFiles() throws Exception {
        this.accountsDataResponsePageFirstCall = mapper
            .readValue(Files
                .readAllBytes(Paths.get("src/test/resources/data/AccountDataResponsePageDtoFirstCall.json")),
                       mapper.getTypeFactory().constructParametricType(ResponsePageDto.class, AccountDataDto.class));
        this.transactionResposeDto = mapper
            .readValue(Files.readAllBytes(Paths.get("src/test/resources/data/TransactionResponse.json")),
                       mapper.getTypeFactory().constructParametricType(List.class, TransactionResponseDto.class));
    }

}
