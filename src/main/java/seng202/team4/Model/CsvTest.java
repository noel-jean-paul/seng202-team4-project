package seng202.team4.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class CsvTest {

    public static void main(String[] args) {
        String file = "seng202_2018_example_data.csv";  //get file to open
        String line;   //empty line into which data will be read
        String csvSplitBy = ",";    //split on the comma

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String[] firstLine = bufferedReader.readLine().split(csvSplitBy);   //read the first line
            System.out.println(firstLine[1]);
            while (!(line = bufferedReader.readLine()).contains("#")) { //read up until the next # symbol, signifying the start of a new activity
                String[] dataPoint = line.split(csvSplitBy);
                System.out.println("(Heart Rate, Time): (" + dataPoint[2] + ", " + dataPoint[1] + ")"); //example of data printout
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}
