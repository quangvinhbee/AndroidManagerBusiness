package com.nhom1.helper;

import java.text.NumberFormat;
import java.util.Currency;

public class Helper {
    public static String getCurrentFromNumber(int number){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("VND"));

        String num = format.format(number);
        return num;
    }
}
