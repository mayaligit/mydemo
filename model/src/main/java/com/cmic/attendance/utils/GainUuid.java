package com.cmic.attendance.utils;

import java.util.UUID;

/**
 * Created by ArcherMind on 2017/8/18.
 */
public class GainUuid {

    public static String getUUID() {
        String str = UUID.randomUUID().toString();
        StringBuilder temp = new StringBuilder();
        temp.append(str.substring(0, 8))
                .append(str.substring(9, 13))
                .append(str.substring(14, 18))
                .append(str.substring(19, 23))
                .append(str.substring(24));
        return temp.toString();
    }
}
