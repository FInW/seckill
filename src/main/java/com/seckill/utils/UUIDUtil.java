package com.seckill.utils;

import java.util.UUID;

/**
 * @author EMINEM
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
