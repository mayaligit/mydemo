package com.cmic.attendance.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取年月日yyyy-MM-dd的字符串
     * @param date
     * @return  String
     */
    public  static String getDateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString=formatter.format(date);
        return dateString;
    }

    /**
     * 获取年月日  yyyy-MM-dd HH:mm:ss 的字符串
     * @param date
     * @return string
     */
    public  static String getDateToStrings(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString=formatter.format(date);
        return dateString;
    }

    /**
     * 获取年月日yyyy-MM-dd的字符串
     * @param date
     * @return  String
     */
    public  static String getDateToStringHMS(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString=formatter.format(date);
        return dateString;
    }
    /**
            * 将时间格式转换成为  yyyy-MM-dd的日期格式
     * @param  stingTodate
     * @return date
     */
    public  static Date getStringsToDate(String stingTodate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateString = null;
        try {
            dateString = formatter.parse(stingTodate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * 将时间格式转换成为  yyyy-MM-dd的日期格式
     * @param date
     * @return date
     */
    public  static Date getStringsToDates(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateString = null;
        try {
            dateString = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * HH:mm:ss
     * @param date
     * @return date
     */
    public  static Integer DatesToHour(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString=formatter.format(date);
        int dates=Integer.parseInt(dateString.split(":")[1]);
        return dates;
    }

    /**
     * 获取当前  年月 格式 yyyy-MM
     * @return
     */
    public static String getCurrMonth(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(new Date());
    }
}
