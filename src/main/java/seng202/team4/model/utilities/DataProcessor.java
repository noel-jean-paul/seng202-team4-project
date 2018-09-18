package seng202.team4.model.utilities;

import seng202.team4.model.data.Profile;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.enums.ActivityType;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class DataProcessor {

    /**
     * Calculates the total distance travelled over the activity data set provided. The returned value is in meters.
     * @param dataList the data set of the activity, must have size > 1.
     * @return the total distance travelled over the duration in meters.
     */
    public static double totalDistance(List<DataRow> dataList) {
        final double earthRadius = earthRadiusCalculation(dataList.get(0).getLatitude());
        double totalDistance = 0;
        int i = 0;
        for (int j = 1; j < dataList.size(); j++) {
            double lat1 = dataList.get(i).getLatitude();
            double lat2 = dataList.get(j).getLatitude();
            double long1 = dataList.get(i).getLongitude();
            double long2 = dataList.get(j).getLongitude();
            double height1 = dataList.get(i).getElevation();
            double height2 = dataList.get(j).getElevation();
            double vertDistance = height2 - height1;
            double horizDistance = haversineCalculation(earthRadius, lat1, lat2, long1, long2);
            totalDistance += Math.sqrt(Math.pow(horizDistance, 2) + Math.pow(vertDistance, 2));
            i++;

        }
        return totalDistance;
    }

    /**
     * @param distance the total distance travelled during the activity in meters.
     * @param time the duration of the activity of type Duration.
     * @return
     */
    public static double calculateAverageSpeed(double distance, Duration time) {
        return (distance / time.getSeconds()) * 3.6;
    }

    /**
     * Calculates the radius of the earth at the given latitudinal coordinates in metres.
     * This method was implemented using the given formula provided by: https://rechneronline.de/earth-radius/
     * @param latitude the latitude at which the given radius will be for - given in decimal degree notation.
     * @return the radius of the earth at the given latitude - in meters.
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

    /**
     * Calculates the distance between two points represented by lat / long coordinates in decimal degree notation, at the
     * given radius of the earth.
     * @param earthRadius the radius of the earth at that particular region.
     * @param lat1 the latitude value of the initial point.
     * @param lat2 the latitude value of the final point.
     * @param long1 the longitude value of the initial point.
     * @param long2 the longitude value of the final point.
     * @return the distance - in metres - between the two points.
     */
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
    public static int calculateDuration(List<DataRow> dataList) {
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

    /**
     * Calculates the calories burned by the user over the given duration.
     * Values calculated here are very rough estimates - Ideal improvement is to calculate based on heart rate as shown
     * through this web page: http://www.shapesense.com/fitness-exercise/calculators/heart-rate-based-calorie-burn-calculator.shtml
     * @param speed the average speed of the user over the duration - in kilometers per hour.
     * @param user the profile of the user who performed the activity.
     * @param duration the time taken for the specific leg of the activity - in seconds.
     * @param activity the type of activity which the user participated in - Run or Walk.
     * @return the approximate number of calories burned by the user for the given activity over the given period of time.
     */
    public static double calculateCalories(double speed, double duration, ActivityType activity, Profile user) {
        double MET;
        if (activity == ActivityType.Walk) {
            MET = walkingMETCalculator(speed);
        } else if (activity == ActivityType.Run) {
            MET = runningMETCalculator(speed);
        } else {
            if (speed <= 7.0) {
                MET = walkingMETCalculator(speed);
            } else {
                MET = runningMETCalculator(speed);
            }
        }
        return (duration / 60) * MET * 3.5 * user.getWeight() / 200;
    }

    /**
     * Calculates the MET of the given running activity which the user participated in.
     * @param speed the speed of the user over the duration of the activity.
     * @return the MET of the activity.
     */
    private static double walkingMETCalculator(double speed) {
        double MET;
        if (speed < 3.2) {
            MET = 2.0;
        } else if (3.2 <= speed && speed < 4.0) {
            MET = 2.8;
        } else if (4.0 <= speed && speed < 4.5) {
            MET = 3.0;
        } else if (4.5 <= speed && speed < 5.2) {
            MET = 3.5;
        } else if (5.2 <= speed && speed < 5.6) {
            MET = 4.3;
        } else if (5.6 <= speed && speed < 6.4) {
            MET = 5.0;
        } else if (6.5 <= speed && speed < 7.2) {
            MET = 7.0;
        } else {
            MET = 8.3;
        }
        return MET;
    }

    /**
     * Calculates the MET of the specific running activity through the user's speed.
     * @param speed the speed at which the user was travelling during this specific leg of the activity.
     * @return the calculated MET.
     */
    private static double runningMETCalculator(double speed) {
        double localMET;
        if (speed < 6.5) {
            localMET = 6.0;
        } else if (6.5 <= speed && speed < 8.0) {
            localMET = 8.3;
        } else if (8.0 <= speed && speed < 9.6) {
            localMET = 9.0;
        } else if (9.6 <= speed && speed < 10.8) {
            localMET = 9.8;
        } else if (10.8 <= speed && speed < 11.2) {
            localMET = 10.5;
        } else if (10.8 <= speed && speed < 12.9) {
            localMET = 11.8;
        } else if (12.9 <= speed && speed < 13.8) {
            localMET = 12.3;
        } else if (13.8 <= speed && speed < 14.5) {
            localMET = 12.8;
        } else if (14.5 <= speed && speed < 16.1) {
            localMET = 14.5;
        } else if (16.1 <= speed && speed < 17.7) {
            localMET = 16;
        } else if (17.7 <= speed && speed < 19.3) {
            localMET = 19.0;
        } else if (19.3 <= speed && speed < 20.9) {
            localMET = 19.8;
        } else {
            localMET = 23.0;
        }
        return localMET;
    }
}
