package com.example.demo.exception;

public class ProblemNotFountException extends ServiceException{
    public ProblemNotFountException() {
        super();
    }

    public ProblemNotFountException(String message) {
        super(message);
    }

    public ProblemNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProblemNotFountException(Throwable cause) {
        super(cause);
    }

    protected ProblemNotFountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
