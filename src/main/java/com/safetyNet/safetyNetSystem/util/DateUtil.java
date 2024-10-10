package com.safetyNet.safetyNetSystem.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static int calculateAge(String birthdate) {
        try {
            LocalDate birthDate = LocalDate.parse(birthdate, DATE_FORMATTER);
            LocalDate currentDate = LocalDate.now();
            return currentDate.getYear() - birthDate.getYear() - (currentDate.getDayOfYear() < birthDate.getDayOfYear() ? 1 : 0);
        } catch (DateTimeParseException e) {
            return -1; //Retourne une date invalide
        }
    }
}
