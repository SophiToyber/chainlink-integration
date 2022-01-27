package wrapper.chainlink.exeption.constants;

public enum ServiceName {
    
    CRYPTOPAYMENT("be-cryptopayment");

    private String providerName;

    ServiceName(String providerName) {
        this.providerName = providerName;
    }

    public String getName() {
        return providerName;
    }

}
