package com.example.demo.exception;

public class updateException extends ServiceException {
    public updateException() {
        super();
    }

    public updateException(String message) {
        super(message);
    }

    public updateException(String message, Throwable cause) {
        super(message, cause);
    }

    public updateException(Throwable cause) {
        super(cause);
    }

    protected updateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
