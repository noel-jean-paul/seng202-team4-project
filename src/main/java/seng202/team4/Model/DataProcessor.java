package seng202.team4.Model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class DataProcessor {

    /**
     * Using the GPS coordinates given by the parsed raw data, calculates the total distance
     * travelled over the course of the activity.
     * @param dataList the ArrayList containing the raw data for the given activity
     * @return the total distance travelled during the activity - in meters.
     */
    public static float totalDistance(ArrayList<ActivityRawData> dataList) {
        double totalDistance = 0;
        int earthRadius = 6371000;
        int j = 0;
        if (dataList != null) {
            for (int i = 1; i < dataList.size(); i++) {
                double startLat = Math.toRadians(dataList.get(i).getLatitude());
                double endLat = Math.toRadians(dataList.get(j).getLatitude());
                double startLong = Math.toRadians(dataList.get(i).getLongitude());
                double endLong = Math.toRadians(dataList.get(j).getLongitude());
                double latDiff = endLat - startLat;
                double longDiff = endLong - startLong;
                double a = 0.5 * (1 - Math.cos(latDiff));
                double b = Math.cos(startLat) * Math.cos(endLat) * 0.5 * (1 - Math.cos(longDiff));
                double distance = 2 * earthRadius * Math.asin(a + b);
                totalDistance += distance;
                System.out.println("Distance is: " + distance);
            }
        } else {
        }
        return (float)totalDistance;
    }

    /**
     * Using the time values from the Activity's raw data ArrayList, calculates the total duration of the activity.
     * If the ArrayList is null, returns 0.
     * @param dataList the ArrayList containg the raw data for the given activity.
     * @return the total duration of the activity - in seconds.
     */
    public static int calculateDuration(ArrayList<ActivityRawData> dataList) {
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