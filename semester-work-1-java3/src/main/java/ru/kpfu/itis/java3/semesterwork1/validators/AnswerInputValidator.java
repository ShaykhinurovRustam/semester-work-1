package ru.kpfu.itis.java3.semesterwork1.validators;

public class AnswerInputValidator {
    private String message;

    public boolean validate(String text) {
        if (text == null) {
            message = "text must be not null";
            return false;
        }
        if (text.isEmpty()) {
            message = "text must be not empty";
            return false;
        }
        return true;
    }

    public String getMessage() {
        return message;
    }

}
