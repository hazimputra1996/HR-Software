package com.hr_software_project.hr_management.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

    public static long getMonthsBetween(Date date1, Date date2) {
        // Convert java.util.Date to java.time.LocalDate
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Calculate the number of months between the two dates
        return ChronoUnit.MONTHS.between(localDate1, localDate2);
    }

    public static boolean isDateBetween(Date dateToCheck, Date startDate, Date endDate) {
        if (dateToCheck == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }

        return !dateToCheck.before(startDate) && !dateToCheck.after(endDate);
    }

    public static boolean isBetween(Long valueToCheck, Long minValue, Long maxValue) {
        if (valueToCheck == null || minValue == null || maxValue == null) {
            throw new IllegalArgumentException("Values cannot be null");
        }

        return valueToCheck >= minValue && valueToCheck <= maxValue;
    }



    }