package com.example.employez.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DayUtil {
    public static long daysBetweenNowAndSpecificDate(Date date) {
        Date sqlDate = Date.valueOf(LocalDate.now());
        try {
            sqlDate = Date.valueOf(date.toString());// Specify SQL date here
        } catch(Exception e) {
            e.printStackTrace();
        }
        LocalDate startDate = sqlDate.toLocalDate();
        LocalDate endDate = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return daysBetween;
    }
}
