package org.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingService {

    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void logException(Exception e) {
        logger.error("Exception occurred: ", e);
    }
}
