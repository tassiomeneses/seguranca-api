package com.example.seguranca.exception;

import com.example.seguranca.util.UTF8Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

public class RuntimeException extends java.lang.RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(RuntimeException.class);

    private final List<Object> params;

    public RuntimeException(Throwable e) {
        super(e);
        params = new ArrayList<>();
    }

    public RuntimeException(String message) {
        super(message);
        params = new ArrayList<>();
    }

    public RuntimeException(String message, Object... params) {
        super(message);
        this.params = Arrays.asList(params);
    }

    public RuntimeException(Throwable cause, String message, Object... params) {
        super(message, cause);
        this.params = Arrays.asList(params);
    }

    public RuntimeException(String message, Throwable cause) {
        super(message, cause);
        params = new ArrayList<>();
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        ResourceBundle bundle = getResourceBundle();
        try {
            message = bundle.getString(message);
        } catch (MissingResourceException e) {
            logger.debug("code {0} not found in property file: {1} ", message, bundle.getBaseBundleName(), e);
        }
        message = MessageFormat.format(message, params.toArray());
        return message;
    }

    public ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("messages", new UTF8Control());
    }

}
