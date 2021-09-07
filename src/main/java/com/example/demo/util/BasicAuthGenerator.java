package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthGenerator {
    public static String generate(String user, String password){
        String auth = user + ":" + password;
        byte[] buf = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(buf);
    }
}
