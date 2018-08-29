package seng202.team4.Model;

import javax.xml.crypto.Data;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DataProcessor {

    /**
     * Using the GPS coordinates given by the parsed raw data, calculates the total distance
     * travelled over the course of the activity.
     * @param dataList the ArrayList containing the raw data for the given activity
     * @return the total distance travelled during the activity - in meters.
     */
    public static double totalDistance(ArrayList<ActivityRawData> dataList) {
        double totalDistance;
        int j = 0;
        for (int i = 1; i < dataList.size(); i++) {
            ActivityRawData start = dataList.get(j);
            ActivityRawData end = dataList.get(i);
            j++;
        }
        return 1;
    }

    public static int calculateDuration(ArrayList<ActivityRawData> dataList) {
        Duration totalDuration = Duration.ZERO;
        int j = 0;
        for (int i = 1; i < dataList.size(); i++) {
            LocalTime startTime = dataList.get(j).getTime();
            LocalTime endTime = dataList.get(i).getTime();
            totalDuration.plus(Duration.between(startTime, endTime));
            j++;
        }
        return (int)totalDuration.getSeconds();
    }

    public static void main(String[] args) {
        ArrayList<ActivityRawData> tester = new ArrayList<ActivityRawData>();
        tester.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:42:28"), 0, 0, 0, 0));
        tester.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:05"), 0, 0, 0, 0));
        tester.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:15"), 0, 0, 0, 0));
        tester.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:34"), 0, 0, 0, 0));
        int time = DataProcessor.calculateDuration(tester);
        System.out.println("Time taken: " + time + "s");
    }
}
