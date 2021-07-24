package com.jacaranda.api.mpapi;

import java.nio.charset.StandardCharsets;

public class Encrypt {
    public static String encrypt(String pwd) {
        int[] key = {134, 8, 187, 0, 251, 59, 238, 74, 176, 180, 24, 67, 227, 252, 205, 80};
        //for good, pwd's length should not be 0
        int pwd_len = pwd.length();
        try {
            if (pwd.length() % 16 != 0) {
                int need_num = 16 - pwd.length() % 16;
                StringBuilder pwd_builder = new StringBuilder();
                pwd_builder.append(pwd);
                for (int i = 0; i < need_num; i++) {
                    pwd_builder.append("0");
                }
                pwd = pwd_builder.toString();
            }
            byte[] pwd_bytes = pwd.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < pwd_bytes.length; i++) {
                pwd_bytes[i] ^= key[i % 16];
            }
            StringBuilder encrypt_builder = new StringBuilder();
            encrypt_builder.append("77726476706e6973617765736f6d6521");
            for (int i = 0; i < pwd_len; i++) {
                byte b = pwd_bytes[i];
                encrypt_builder.append(String.format("%02x", b));
            }
            return encrypt_builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
