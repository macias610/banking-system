package com.banking.chestnut.deposit.helpers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DateHelper {
    public static Date currentDate() {
        return Date.valueOf(
                      LocalDate.now(ZoneId.of("Europe/Warsaw")).
                                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }
    
    public static LocalDateTime currentTimestamp() {
        return LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
    }
    
    public static Float daysInCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate());
        return (float)calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }
}
