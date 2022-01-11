package cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.exception;

/**
 * @author quicksand - 2022/1/11
 */
public class RateLimitException extends Exception {

    public RateLimitException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
