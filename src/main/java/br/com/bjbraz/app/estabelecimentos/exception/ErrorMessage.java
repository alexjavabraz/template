package br.com.bjbraz.app.estabelecimentos.exception;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @version $VERSION
 */
public class ErrorMessage {

    public static final ErrorMessage UNEXPECTED_ERROR = new ErrorMessage(-1, "Unexpected error: {0}");
    public static final ErrorMessage NULL_ARGUMENT = new ErrorMessage(-2, "Null argument");
    public static final ErrorMessage VALIDATION_ERROR = new ErrorMessage(-3, "Validation error: {0} value: {1}");
    public static final ErrorMessage CONVERTER_ERROR = new ErrorMessage(-4, "Converter error: {0} value: {1}");
    public static final ErrorMessage TIBCO_USER_EXISTS_ERROR = new ErrorMessage(-100, "User {0} already exists");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMessage.class);

    private int code;
    private String message;

    protected ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMesssage() {
        return message;
    }

    public String formatMesssage(Object... args) {
        String formatedMessage = message;
        try {
            formatedMessage = MessageFormat.format(message, args);
        }
        catch (IllegalArgumentException e) {
            LOGGER.warn("\"" + message + "\" " + e, e);
        }
        return addCode(formatedMessage);
    }

    private String addCode(String s) {
        return "[" + code + ": " + s + "]";
    }

}
