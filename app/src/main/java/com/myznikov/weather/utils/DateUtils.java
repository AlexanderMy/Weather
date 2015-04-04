package com.myznikov.weather.utils;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MS Creator on 04.04.2015.
 */
public class DateUtils {
    public static String getDate(String time){
        long dv = Long.valueOf(time)*1000;
        Date df = new java.util.Date(dv);
        String result = new SimpleDateFormat("MM dd, yyyy hh:mma").format(df);
        return result;
    }
}
