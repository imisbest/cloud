package com.csw.util;

import java.sql.Timestamp;
import java.util.Date;

public class SystemTime {
    public static Timestamp getTime() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static Timestamp getAfterTime(Integer second) {
        Date date = new Date();
        return new Timestamp(date.getTime() + second * 1000);
    }
}
