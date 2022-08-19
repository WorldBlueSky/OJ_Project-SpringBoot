package com.example.demo.exception;

public class deleteException extends ServiceException {
    public deleteException() {
        super();
    }

    public deleteException(String message) {
        super(message);
    }

    public deleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public deleteException(Throwable cause) {
        super(cause);
    }

    protected deleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
