package com.opl.cbdc.utils.common;

import java.util.Base64;

public class CustomBase64Encryption {

    public static String encode(String plainTxt) {

        StringBuilder stringBuilder = new StringBuilder();
        int[] a1 = new int[plainTxt.toCharArray().length];
        int i = 0;
        for (char a : plainTxt.toCharArray()) {
            a1[i] = a + 65;
            i++;
        }
        for (int i1 : a1) {
            stringBuilder.append((char) i1);
        }

        return new String(Base64.getEncoder().encode(stringBuilder.toString().getBytes()));
    }

    public static String decode(String encodedStr) {

        StringBuilder stringBuilder = new StringBuilder();
        String decoded = new String(Base64.getDecoder().decode(encodedStr));
        int[] a1 = new int[decoded.toCharArray().length];
        int i = 0;
        for (char a : decoded.toCharArray()) {
            a1[i] = (int) a - 65;
            i++;
        }
        for (int i1 : a1) {
            stringBuilder.append((char) i1);
        }
        return stringBuilder.toString();
    }
}

