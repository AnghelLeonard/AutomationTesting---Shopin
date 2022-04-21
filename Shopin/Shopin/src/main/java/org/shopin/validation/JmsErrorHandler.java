package org.shopin.validation;

import org.shopin.controller.ErrorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class JmsErrorHandler implements ErrorHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @Override
    public void handleError(Throwable t) {
        LOGGER.error("++++++++++++++++++++++++++++++++++++++++++");
        LOGGER.error("Error in JMS listener", t);
        LOGGER.error("++++++++++++++++++++++++++++++++++++++++++");
    }
}
