package com.hr_software_project.hr_management.utils;

import com.hr_software_project.hr_management.entity.PublicHolidayDO;
import com.hr_software_project.hr_management.error.ServiceErrorCodes;
import com.hr_software_project.hr_management.error.ServiceException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

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

    public static Long getWorkingDaysBetween(Date startDate, Date endDate, List<PublicHolidayDO> allPublicHoliday, Boolean includePublicHoliday, Boolean includeWeekend, Integer weeklyWorkingDays) {
        if (startDate == null || endDate == null) {
            throw new ServiceException(ServiceErrorCodes.DATE_NULL);
        }

        if (startDate.after(endDate)) {
            throw new ServiceException(ServiceErrorCodes.WRONG_DATE_RANGE);
        }

        long workingDays = 0;
        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (includeWeekend) {
            weeklyWorkingDays = 7;
        }
        while (!localStartDate.isAfter(localEndDate)) {
            if (localStartDate.getDayOfWeek().getValue() < weeklyWorkingDays+1) {
                workingDays++;
            }

            localStartDate = localStartDate.plusDays(1);
        }

        if (includePublicHoliday) {
            return workingDays;
        }

        for (PublicHolidayDO publicHoliday : allPublicHoliday) {
            if (isDateBetween(publicHoliday.getDate(), startDate, endDate)) {
                workingDays--;
            }
        }

        return workingDays;

    }

    public static Double getHoursBetween(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            throw new ServiceException(ServiceErrorCodes.DATE_NULL);
        }

        if (startTime.after(endTime)) {
            throw new ServiceException(ServiceErrorCodes.WRONG_DATE_RANGE);
        }

        long diff = endTime.getTime() - startTime.getTime();
        return (double) diff / (60 * 60 * 1000);
    }

    public static Date getStartDateBasedCutOff(Date statementDate, Integer cuttOffDay){
        if (statementDate == null || cuttOffDay == null) {
            throw new ServiceException(ServiceErrorCodes.DATE_NULL);
        }

        LocalDate localStatementDate = statementDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Based on localStatementDate, find last month's date based on cuttOffDay
        LocalDate localStartDate = localStatementDate.minusMonths(1).withDayOfMonth(cuttOffDay);

        return Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    }

    public static Date getEndDateBasedCutOff(Date statementDate, Integer cuttOffDay){
        if (statementDate == null || cuttOffDay == null) {
            throw new ServiceException(ServiceErrorCodes.DATE_NULL);
        }

        LocalDate localStatementDate = statementDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Based on localStatementDate, find last month's date based on cuttOffDay
        LocalDate localStartDate = localStatementDate.withDayOfMonth(cuttOffDay).minusDays(1);

        return Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    }



    }