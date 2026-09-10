package com.challan;

import java.util.*;

public class ChallanService {
    private Map<String, Integer> violationCountMap = new HashMap<>();
    private Map<String, Double> outstandingFines = new HashMap<>();

    public double generateChallan(String vehicleNo, String violationType, double currentSpeed, double limitSpeed) {
        if (vehicleNo == null || vehicleNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid vehicle number.");
        }

        double fine = 0.0;
        if ("OVERSPEEDING".equalsIgnoreCase(violationType)) {
            if (currentSpeed > limitSpeed) {
                fine = (currentSpeed - limitSpeed) * 100; // Rs 100 per km/h over limit
            }
        } else if ("SIGNAL_VIOLATION".equalsIgnoreCase(violationType)) {
            fine = 1000.0;
        } else if ("PARKING".equalsIgnoreCase(violationType)) {
            fine = 500.0;
        }

        // Apply penalty for repeat offenders
        int count = violationCountMap.getOrDefault(vehicleNo, 0) + 1;
        violationCountMap.put(vehicleNo, count);

        if (count > 1) {
            fine += 500.0; // Repeat penalty surcharge
        }

        outstandingFines.put(vehicleNo, outstandingFines.getOrDefault(vehicleNo, 0.0) + fine);
        return fine;
    }

    public boolean payChallan(String vehicleNo, double amount) {
        double total = outstandingFines.getOrDefault(vehicleNo, 0.0);
        if (total > 0 && amount >= total) {
            outstandingFines.put(vehicleNo, 0.0);
            return true;
        }
        return false;
    }

    public double getOutstandingFine(String vehicleNo) {
        return outstandingFines.getOrDefault(vehicleNo, 0.0);
    }
}
