package wrapper.chainlink.exeption;

public class InnerServiceException extends BaseException {

    private static final long serialVersionUID = 9022995101956481320L;

    public InnerServiceException(String message, Throwable throwable) {
        super(ExceptionCode.INTERNAL_SERVICE_EXCEPTION, message, throwable);
    }

    public InnerServiceException(String message) {
        super(ExceptionCode.INTERNAL_SERVICE_EXCEPTION, message);
    }

}
