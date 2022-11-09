package ru.kpfu.itis.java3.semesterwork1.validators;

public class RegistrationInputValidator {
    private String message = "";

    public boolean validate(String login, String password) {
        if (login == null || password == null) {
            message = "fields cannot be null";
            return false;
        }
        if (login.isEmpty()) {
            message = "empty login field";
            return false;
        }
        if (password.isEmpty()) {
            message = "empty password field";
            return false;
        }
        return true;
    }

    public String getMessage() {
        return message;
    }
}
