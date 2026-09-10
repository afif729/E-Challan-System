package com.challan;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChallanServiceTest {

    @Test
    public void testOverspeedingChallan() {
        ChallanService service = new ChallanService();
        // 80 - 60 = 20 * 100 = 2000
        double fine = service.generateChallan("TN37AB1234", "OVERSPEEDING", 80, 60);
        assertEquals(2000.0, fine, 0.001);
    }

    @Test
    public void testRepeatOffenderPenalty() {
        ChallanService service = new ChallanService();
        service.generateChallan("TN37AB1234", "PARKING", 0, 0); // 500
        double secondFine = service.generateChallan("TN37AB1234", "PARKING", 0, 0); // 500 + 500 penalty = 1000
        assertEquals(1000.0, secondFine, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidVehicleNumber() {
        ChallanService service = new ChallanService();
        service.generateChallan("", "PARKING", 0, 0);
    }
}
