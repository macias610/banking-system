package com.banking.chestnut.credit.helpers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    public static Date currentDate() {
        return Date.valueOf(
                LocalDate.now(ZoneId.of("Europe/Warsaw")).
                        format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    public static Date addMonths(Date date, long months){
        LocalDate now = date.toLocalDate();
        LocalDate newDate = now.plusMonths(months);


        Date finalDate = Date.valueOf(
                newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

//        Date finalDate = Date.valueOf(
//                LocalDate.now(ZoneId.of("Europe/Warsaw")).
//                        format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//        );


        return finalDate;
    }

    public static LocalDateTime currentTimestamp() {
        return LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
    }
}