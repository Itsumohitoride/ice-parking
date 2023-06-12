import ParkingDemo.PingEcho;
import com.zeroc.Ice.Current;

import java.io.IOException;
import java.net.InetAddress;

public class PingEchoI implements PingEcho {
    @Override
    public String ping(String ipAddress, Current current) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);

            if(inetAddress.isReachable(5000)){
                return "The resource " + ipAddress + " is reachable";
            }

        } catch (IOException exception){
            printMessage("Error checking the availability of the resource: " + exception.getMessage());
        }
        return "The resource " + ipAddress + " is not reachable";
    }

    private void printMessage(String message){
        System.out.println(message);
    }
}
