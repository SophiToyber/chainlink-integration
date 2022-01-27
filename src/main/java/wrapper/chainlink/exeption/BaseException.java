package wrapper.chainlink.exeption;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 7143144331542329078L;

    private final ExceptionCode exceptionCode;
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    
    ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public BaseException(ExceptionCode exceptionCode) {
        super(exceptionCode.getDebugMessage());
        this.exceptionCode = exceptionCode;
    }

    BaseException(ExceptionCode exceptionCode, String message, Throwable throwable) {
        super(message, throwable);
        this.exceptionCode = exceptionCode;
    }

    BaseException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
    
}
