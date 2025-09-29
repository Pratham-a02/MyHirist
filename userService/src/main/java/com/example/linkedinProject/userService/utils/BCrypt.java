package com.example.linkedinProject.userService.utils;

import static org.mindrot.jbcrypt.BCrypt.checkpw;
import static org.mindrot.jbcrypt.BCrypt.hashpw;

public class BCrypt {

    public static String hash(String s){
        return hashpw(s, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    public static boolean match(String passwordText,String passwordHashed){
        return checkpw(passwordText,passwordHashed);
    }
}
