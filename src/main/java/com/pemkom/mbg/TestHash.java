package com.pemkom.mbg;

import com.pemkom.objects.SecurityUtils;

public class TestHash {

    public static void main(String[] args) {
        System.out.println(SecurityUtils.hashSHA256("admin123"));
    }
}
