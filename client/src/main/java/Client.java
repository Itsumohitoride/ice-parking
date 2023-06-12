import ParkingDemo.TotalPayablePrx;
import com.zeroc.Ice.Communicator;

import static com.zeroc.Ice.Util.initialize;

public class Client {
    public static void main(String[] args)
    {
        try(Communicator communicator = initialize(args, "client.cfg"))
        {
            TotalPayablePrx service = TotalPayablePrx.checkedCast(
                    communicator.propertyToProxy("Parking.Proxy"));

            if(service == null)
            {
                throw new Error("Invalid proxy");
            }
            String total = service.parkingService("HOALKSL");
            printMessage(total);
        }
    }

    private static void printMessage(String message){
        System.out.println(message);
    }
}