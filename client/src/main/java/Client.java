import ParkingDemo.TotalPayablePrx;
import com.zeroc.Ice.Communicator;

import java.util.Scanner;

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

            boolean verify = true;

            while (verify){

                System.out.println("Por favor ingrese la placa de su vehiculo\nSi desea salir de la app digite salir");
                Scanner scanner = new Scanner(System.in);
                String licensePlate = scanner.nextLine();

                if (licensePlate.equalsIgnoreCase("salir")){
                    verify = false;

                }else {
                    printMessage(service.parkingService(licensePlate));
                }
            }
        }
    }

    private static void printMessage(String message){
        System.out.println(message);
    }
}