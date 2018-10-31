package com.example.wz.lovingpets.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String datePattern = "yyyy-MM-dd hh:mm:ss";
    public static String datePattern1 = "yyyy-MM-dd";
    public static SimpleDateFormat dateFormate = new SimpleDateFormat(datePattern);
    public static SimpleDateFormat dateFormate1 = new SimpleDateFormat(datePattern1);

    //类型转换（日期型转换成字符串型数据）
    public static String DateToString(Date date) {
        if (date != null) {
            synchronized (dateFormate1) {
                return dateFormate1.format(date);
            }
        }
        return null;
    }
    //类型转换（日期型转换成字符串型数据）
    public static Date stringToDate(String date) {
        if (date != null) {
            synchronized (dateFormate1) {
                try {
                    return dateFormate1.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
