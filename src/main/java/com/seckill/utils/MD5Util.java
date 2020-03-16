package com.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author EMINEM
 */
public class MD5Util {

    private static final String salt = "1a2b3c4d";

    private static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String md5BySalt(String password, String salt) {
        String str = salt.charAt(0)+salt.charAt(2) + password +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static void main(String[] args) {
        System.out.println(md5BySalt("d3b1294a61a07da9b49b6e22b2cbd7f9", "123456"));//d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(md5("123456"));
//		System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d"));
//		System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));//b7797cce01b4b131b433b6acf4add449
    }

}
