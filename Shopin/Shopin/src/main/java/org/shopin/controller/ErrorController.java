package org.shopin.controller;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.shopin.pojo.GenericMessage;
import org.shopin.service.IMessageSenderService;
import org.shopin.util.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @Value("${org.error.code}")
    private String code;

    @Autowired
    private IMessageSenderService messageSenderService;

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {

        LOGGER.error("\n++++++++++++++++++++++++++++++++++++++++++");
        LOGGER.error("\nException during execution of application:", throwable);
        LOGGER.error("\n++++++++++++++++++++++++++++++++++++++++++");

        if (throwable instanceof ConstraintViolationException) {

            Set<ConstraintViolation<?>> set = ((ConstraintViolationException) throwable).getConstraintViolations();

            String error = "Malicious data: ";
            for (ConstraintViolation<?> next : set) {
                error = error + next.getInvalidValue() + "|";
            }

            messageSenderService.sendMessage("mailbox", new GenericMessage("", "", error, MessageType.MALICIOUS));
        }

        String errorMessage = (throwable != null
                ? (throwable.getMessage().startsWith(code)
                ? throwable.getMessage().substring(code.length() + 1) : "Unknown error. Please, tell us what just happened at org@fashion.com") : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error/error";
    }

}
