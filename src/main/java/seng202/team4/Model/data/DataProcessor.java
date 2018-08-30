package seng202.team4.model.data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class DataCalculator {
    public static double totalDistance(ArrayList<DataRow> dataList) {
        final double earthRadius = earthRadiusCalculation(dataList.get(0).getLatitude());
        double totalDistance = 0;
        int j = 0;
        if (dataList != null) {
            for (int i = 1; i < dataList.size(); i++) {
                double lat1 = dataList.get(j).getLatitude();
                double lat2 = dataList.get(i).getLatitude();
                double long1 = dataList.get(j).getLongitude();
                double long2 = dataList.get(i).getLongitude();
                double distance = haversineCalculation(earthRadius, lat1, lat2, long1, long2);
                totalDistance += distance;
                j++;
            }
        }
        return totalDistance;
    }

    /**
     * Calculates the radius of the earth at the given latitudinal coordinates in metres.
     * This method was implemented using the given formula provided by: https://rechneronline.de/earth-radius/
     * @param latitude the latitude at which the given radius will be for.
     * @return the radius of the earth.
     */
    public static double earthRadiusCalculation(double latitude) {
        // Trigonometric functions in Java ALWAYS use radians instead of degrees for their calculations.
        latitude = Math.toRadians(latitude);
        double equatorRadius = 6378137;
        double poleRadius = 6356752;
        double numerator = Math.pow((Math.pow(equatorRadius, 2) * Math.cos(latitude)), 2) + Math.pow((Math.pow(poleRadius, 2) * Math.sin(latitude)), 2);
        double denominator = Math.pow((equatorRadius * Math.cos(latitude)), 2) + Math.pow((poleRadius * Math.sin(latitude)), 2);
        return Math.sqrt(numerator / denominator);
    }

    private static double haversineCalculation(double earthRadius, double lat1, double lat2, double long1, double long2) {
        double diffLat = Math.toRadians(lat2 - lat1);
        double diffLong = Math.toRadians(long2 - long1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.pow(Math.sin(diffLat / 2), 2) + (Math.cos(lat1) *
                Math.cos(lat2) * Math.pow(Math.sin(diffLong / 2), 2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    /**
     * Using the time values from the Activity's raw data ArrayList, calculates the total duration of the activity.
     * If the ArrayList is null, returns 0.
     * @param dataList the ArrayList containg the raw data for the given activity.
     * @return the total duration of the activity - in seconds.
     */
    public static int calculateDuration(ArrayList<DataRow> dataList) {
        Duration totalDuration = Duration.ZERO;
        int j = 0;
        if (dataList != null) {
            for (int i = 1; i < dataList.size(); i++) {
                LocalTime startTime = dataList.get(j).getTime();
                LocalTime endTime = dataList.get(i).getTime();
                totalDuration = totalDuration.plus(Duration.between(startTime, endTime));
                j++;
            }
            return (int) totalDuration.getSeconds();
        } else {
            return 0;
        }
    }
}
