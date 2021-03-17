package fr.diginamic.agence.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    public static String DATE_FORMAT_OUTPUT = "d/MM/yyyy";
    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    public static String formatted(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT_OUTPUT));
    }

    public static <DateTime> LocalDate convert(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
    }
}
