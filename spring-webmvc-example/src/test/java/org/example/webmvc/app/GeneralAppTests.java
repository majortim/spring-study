package org.example.webmvc.app;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

public class GeneralAppTests {
    @Test
    public void testLocalDate(){

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
        System.out.println(localDate);
        System.out.println(DateTimeFormatter.ISO_DATE.format(localDate));

        System.out.println(LocalDate.parse("2021-08-16", DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.KOREA)));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.KOREA).format(localDate));

    }
}
