package org.example.webApplicationShopSpringBoot.service;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptUtil {
    public static String hashPassword(String originalPassword) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(originalPassword, salt);
    }

    public static boolean checkPassword(String enteredPassword, String hash) {
        return BCrypt.checkpw(enteredPassword, hash);
    }
}
