package com.ltrha.ticket.utils.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public enum CommonDateFormat {
        DD_MM_YYYY("dd-MM-yyyy"),
        YYYY_MM_DD("yyyy-MM-dd"),
        DD_MM_YYYY_HH_MM("dd-MM-yyyy HH:mm");
        private final String format;

        CommonDateFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }

    public static String formatLocalDate(LocalDate date, CommonDateFormat format) {
        return date.format(DateTimeFormatter.ofPattern(format.getFormat()));
    }

    public static String formatLocalDateTime(LocalDateTime date, CommonDateFormat format) {
        return date.format(DateTimeFormatter.ofPattern(format.getFormat()));
    }

    public static LocalDateTime parseLocalDateTimeFromString(String date, CommonDateFormat format) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format.getFormat()));
    }
}
