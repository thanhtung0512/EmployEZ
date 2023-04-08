package com.example.employez.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static Date dateFromString(String dateString) throws ParseException {
        // your input string date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = dateFormat.parse(dateString); // parse the string date to a java.util.Date object
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // convert the java.util.Date object to a java.sql.Date object
        return sqlDate;
    }
}
