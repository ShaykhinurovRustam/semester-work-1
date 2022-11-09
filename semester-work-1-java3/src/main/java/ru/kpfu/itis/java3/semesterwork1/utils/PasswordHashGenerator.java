package ru.kpfu.itis.java3.semesterwork1.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordHashGenerator {

    public String generateHashedPassword(String password) {
        String salt = "</3lullaby?";
        return DigestUtils.md5Hex(password + salt);
    }
}
