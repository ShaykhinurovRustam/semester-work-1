package ru.kpfu.itis.java3.semesterwork1.validators;

public class RatingInputValidator {
    private String message = "";

    public boolean validate(String rating) {
        if (rating == null) {
            message = "rating cannon be null";
            return false;
        }
        if (!isDigit(rating)) {
            message = "incorrect data for int rating";
            return false;
        }
        if (Integer.parseInt(rating) < 0) {
            message = "rating must be positive";
            return false;
        }
        return true;
    }

    public String getMessage() {
        return message;
    }

    private boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
