
package com.example.financefx;

import com.example.financefx.util.DateUtils;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class CalculationTests {

    @Test
    void monthIso_shouldFormatYYYY_MM() {
        String m = DateUtils.monthIso(LocalDate.of(2025, 11, 5));
        assertEquals("2025-11", m);
    }

    @Test
    void toIso_shouldFormatYYYY_MM_DD() {
        String s = DateUtils.toIso(LocalDate.of(2025, 3, 9));
        assertEquals("2025-03-09", s);
    }
}
