package com.hoolah.utils;

import com.hoolah.Constants;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void parseDateTime() {
        String dateTime = "20/08/2020 12:45:58";
        LocalDateTime localDateTime = Utils.parseDateTime(dateTime);
        assertNotNull(localDateTime);
    }

    @Test
    void decimalValue() {
        assertEquals(BigDecimal.valueOf(32.50).setScale(2, RoundingMode.HALF_UP),Utils.decimalValue(32.50));
    }

    @Test
    void interpretInput() {
        String input = "CSV|,";
        String output []  = Utils.interpretInput(input);
        assertNotNull(output);
        assertEquals(2, output.length);
        assertEquals(Constants.COMMA_DELIMITER, output[1]);
        assertEquals(Constants.CSV, output[0]);
    }
}