
package com.example.financefx.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String toIso(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }
    public static String monthIso(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }
}
