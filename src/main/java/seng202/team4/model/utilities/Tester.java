package seng202.team4.model.utilities;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Tester {

    public static void main(String args[])
    {
        try{
            String host="www.google.com";
            int port=80;
            int timeOutInMilliSec= 200;// 5 Seconds
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), timeOutInMilliSec);
            System.out.println("Internet is Available");
        }
        catch(Exception ex){
            System.out.println("No Connectivity");
        }
    }
}
