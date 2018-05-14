package com.crm.application.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class LocalExceptionHandler implements ExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalExceptionHandler.class);

    @Override
    public void handleException(RepeatContext repeatContext, Throwable throwable) throws Throwable {
        if (throwable instanceof FlatFileParseException) {
            FlatFileParseException fe = (FlatFileParseException) throwable;
            LOGGER.error("!!!! FlatFileParseException, line # is: " + fe.getLineNumber());
        }
        LOGGER.error("!!!! Message : " + throwable.getMessage());

    }


}
