package ru.kpfu.itis.java3.semesterwork1.validators;

public class QuestionInputValidator {
    private String message;

    public QuestionInputValidator() {
        message = "";
    }

    public boolean validate(String title, String description) {
        if (title == null || description == null) {
            message = "field must be not null";
            return false;
        }
        if (title.isEmpty()) {
            message = "title field must be not empty";
            return false;
        }
        if (title.length() > 20) {
            message = "title must have len < 20";
            return false;
        }
        return true;
    }

    public String getMessage() {
        return message;
    }
}
