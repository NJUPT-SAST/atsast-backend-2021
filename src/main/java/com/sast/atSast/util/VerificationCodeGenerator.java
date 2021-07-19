package com.sast.atSast.util;

import java.util.Random;

/**
 * @author: 風楪fy
 * @create: 2021-07-18 18:14
 **/
public class VerificationCodeGenerator {
    public static String getVerificationCode(int n){
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }
}
