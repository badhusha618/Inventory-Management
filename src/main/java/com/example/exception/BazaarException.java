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
public class BazaarException extends Exception {

    /**
     * Default Serial ID
     */
    private static final long serialVersionUID = 345456L;

    public BazaarException() {

    }

    public BazaarException(String message) {
        super(message);
    }

    public BazaarException(Throwable cause) {
        super(cause);
    }

    public BazaarException(String message, Throwable cause) {
        super(message, cause);
    }

    public BazaarException(String message, Error error) {
        super(message, error);
    }
}
