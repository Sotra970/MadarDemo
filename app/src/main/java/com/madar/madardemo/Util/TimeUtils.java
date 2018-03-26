package com.madar.madardemo.Util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Ahmed on 8/20/2017.
 */

public class TimeUtils {

    public final static String LANGUAGE_AR = "ar";
    public final static String LANGUAGE_EN = "en";

    public final static int LENGTH_SHORT = Calendar.SHORT;
    public final static int LENGTH_LONG = Calendar.LONG;

    public final static String FORMAT_DATE_WITH_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_DATE_WITH_TIME_FULL = "yyyy-MM-dd hh:mm aa";
    public final static String FORMAT_DATE_NO_TIME = "yyyy-MM-dd";


    public static String getMonth(long epoch){
        Date date = new Date(epoch*1000L);

        SimpleDateFormat month = new SimpleDateFormat("MMM", new Locale("en"));
        month.setTimeZone(TimeZone.getDefault());

        return month.format(date);
    }

    public static String getDayOfMonth(long epoch){
        Date date = new Date(epoch*1000L);

        SimpleDateFormat day = new SimpleDateFormat("dd", new Locale("en"));
        day.setTimeZone(TimeZone.getDefault());

        return day.format(date);
    }

    public static String getYear(String date, String dateFormat, String outputLang, int outputLength){
        if(date != null ){
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat(dateFormat, new Locale("en"));

            Date parse = null;
            try {
                parse = simpleDateFormat.parse(date);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            if(parse != null){
                Calendar c = Calendar.getInstance();
                c.setTime(parse);

                return c.getDisplayName(
                        Calendar.YEAR,
                        outputLength,
                        new Locale(outputLang)
                );

            }
        }

        return "";
    }

    public static String getDayOfMonth(String date, String dateFormat, String outputLang){
        if(date != null ){
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat(dateFormat, new Locale("en"));

            Date parse = null;
            try {
                parse = simpleDateFormat.parse(date);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            if(parse != null){
                Calendar c = Calendar.getInstance();
                c.setTime(parse);

                return c.get(
                        Calendar.DAY_OF_MONTH
                ) + "";

            }
        }

        return "";
    }

    public static String getMonthOfYear(String date, String dateFormat, String outputLang, int outputLength){
        if(date != null ){
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat(dateFormat, new Locale("en"));

            Date parse = null;
            try {
                parse = simpleDateFormat.parse(date);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            if(parse != null){
                Calendar c = Calendar.getInstance();
                c.setTime(parse);

                return c.getDisplayName(
                        Calendar.MONTH,
                        outputLength,
                        new Locale(outputLang)
                );

            }
        }

        return "";
    }

    public static long getTimeNow(){
        return System.currentTimeMillis();
    }

    public static String removeTimeFromDate(String date){
        if(date != null && !date.isEmpty()){
            try {
                int index = date.indexOf(" ");
                String newDate = date.substring(0, index);
                return newDate;
            }
            catch (Exception e){

            }
        }

        return "";
    }

    public static String removeDateFromDate(String date){
        if(date != null && !date.isEmpty()){
            try {
                int index = date.indexOf(" ");
                String timeWithMillis = date.substring(index, date.length() - 1);
                if(timeWithMillis != null && !timeWithMillis.isEmpty()){
                    int colonLastIndex = timeWithMillis.lastIndexOf(':');
                    if(colonLastIndex != -1){
                        String time = timeWithMillis.substring(0, colonLastIndex);
                        return time;
                    }
                }
            }
            catch (Exception e){

            }
        }
        return "";
    }

    public static  Date getDate(String date , String format){
        Log.e("date" , date) ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format , Locale.ENGLISH) ;
        try {
            return  simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return  null;
        }
    }
    public static  String changeTimeFormatFromHmsToHma(String date){
            Log.e("date" , date) ;
            SimpleDateFormat hms_format = new SimpleDateFormat("hh:mm:ss" , Locale.ENGLISH) ;
            SimpleDateFormat hma_format = new SimpleDateFormat("hh:mm aa" , Locale.ENGLISH) ;
            try {
                Date hms_data = hms_format.parse(date) ;
                return   hma_format.format(hms_data) ;
            } catch (ParseException e) {
                e.printStackTrace();
                return  null;
            }
        }


    public static double compare2Dates(Date a , Date b){
       return  a.compareTo(b) ;
    }


    public static double compareNowDate(Date a ){
        return  a.compareTo(Calendar.getInstance().getTime()) ;
    }




}
