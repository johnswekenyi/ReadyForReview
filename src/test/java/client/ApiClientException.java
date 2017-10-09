package client;

public class ApiClientException extends Exception {

    public ApiClientException() {
    }

    public ApiClientException(String message) {
        super(message);
    }

    public ApiClientException(Throwable cause) {
        super(cause);
    }

    public ApiClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiClientException(String message, Throwable cause,
                              boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
