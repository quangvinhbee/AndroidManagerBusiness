package com.nhom1.helper;

import android.util.Log;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class Helper {

    public static boolean check8hours(String startAt, String endAt) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(startAt);
            d2 = format.parse(endAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        if (diffHours >= 8) return true;
        else return false;
    }

    public static String getCurrentFromNumber(int number) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("VND"));

        String num = format.format(number);
        return num;
    }

    public static String getCurrentTime() {

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String time = hour + ":" + minute + ":" + second;
        return time;

    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "-" + (month + 1) + "-" + year;
        return date;
    }

    public static Boolean checkDateInCurrentMonth(String date) {
        String currentMonth = getMonthInDate(getCurrentDate());
        String month = getMonthInDate(date);

        Log.e("currentMonth / month", currentMonth + "/" + month);

        if (currentMonth.equals(month)) {
            return true;
        } else
            return false;
    }


    private static String getMonthInDate(String date) {
        String Str = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date d = (Date) formatter.parse(date);
            Str = String.valueOf(d.getMonth());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Str;
    }
}
