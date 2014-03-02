package com.luxoft.bankapp.exceptions;

import java.sql.SQLException;

/**
 * Created by aili on 02.03.14.
 */
public class ImpossibleToSaveInDBException extends SQLException {
    public ImpossibleToSaveInDBException(String message) {
        super(message);
    }
}
