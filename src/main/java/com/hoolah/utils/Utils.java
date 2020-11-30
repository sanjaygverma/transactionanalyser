package com.hoolah.utils;

import com.hoolah.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static final DateTimeFormatter DDMMYYYY_FORMATTER = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static LocalDateTime parseDateTime(String dateTime)
    {
        return LocalDateTime.parse(dateTime, DDMMYYYY_FORMATTER);
    }


    public static BigDecimal decimalValue(Double d) {
        return BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP);
    }

    public static String[] interpretInput(String input)
    {
        String [] vals = input.split(Constants.PIPE);
        if(vals.length != 2)
            throw new IllegalArgumentException("Need param in format 'Type,DELIMITER' " +
                    "example: CSV|, ");
        return vals;
    }
}
