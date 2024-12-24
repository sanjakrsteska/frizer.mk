package mk.frizer.utilities;

import org.springframework.stereotype.Component;

@Component
public class DistanceCalculator {
    public DistanceCalculator() {}

    private static final double EARTH_RADIUS_KM = 6371;

    public double getDistance(double userLat, double userLon, double salonLat, double salonLon) {
        double dLat = Math.toRadians(salonLat - userLat);
        double dLon = Math.toRadians(salonLon - userLon);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(salonLat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance in kilometers
        return EARTH_RADIUS_KM * c;
    }
}