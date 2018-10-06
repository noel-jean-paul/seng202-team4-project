package seng202.team4.model.utilities;

public class Tester {

    public static void main(String args[])
    {
        try {
            String OS = System.getProperty("os.name");
            Process pingProcess;
            if (OS.contains("Windows")) {
                pingProcess = java.lang.Runtime.getRuntime().exec("ping -n 2 -w 200 www.google.com");
            } else {
                pingProcess = java.lang.Runtime.getRuntime().exec("ping -c 2 -w 400 www.google.com");
            }
            int returnVal = pingProcess.waitFor();
            boolean reachable = (returnVal == 0);
            System.out.println("Works yo: " + reachable);
        } catch (Exception e) {
            System.out.println("Doesn't work yo");
        }
    }
}
