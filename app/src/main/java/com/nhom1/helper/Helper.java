package com.nhom1.helper;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;

public class Helper {
    public static String getCurrentFromNumber(int number){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("VND"));

        String num = format.format(number);
        return num;
    }
    public static String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "/" + (month+1) + "/" + year;
        return date;
    }
}
