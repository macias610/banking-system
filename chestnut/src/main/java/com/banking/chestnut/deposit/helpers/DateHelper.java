package com.banking.chestnut.deposit.helpers;

import org.springframework.format.datetime.DateFormatter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    public static Date currentDate(){
        return Date.valueOf(
                      LocalDate.now(ZoneId.of("Europe/Warsaw")).
                      format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }
    
    public static LocalDateTime currentTimestamp(){
        return LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
    }
}
