package enums;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    NOT_FOUND_ERROR_MESSAGE("Not Found"),
    VALIDATION_ERROR_MESSAGE("One or more validation errors occurred"),
    PUBLISH_DATE_ERROR_MESSAGE("The JSON value could not be converted to System.DateTime");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
