/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.example.exception;

/**
 * @Created By BAD_SHA
 */
public class BazzarException extends Exception {

    /**
     * Default Serial ID
     */
    private static final long serialVersionUID = 345456L;

    public BazzarException() {

    }

    public BazzarException(String message) {
        super(message);
    }

    public BazzarException(Throwable cause) {
        super(cause);
    }

    public BazzarException(String message, Throwable cause) {
        super(message, cause);
    }

    public BazzarException(String message, Error error) {
        super(message, error);
    }
}
