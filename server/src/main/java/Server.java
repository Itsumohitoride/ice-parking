import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.Communicator;

import java.util.ArrayList;

import static com.zeroc.Ice.Util.initialize;
import static com.zeroc.Ice.Util.stringToIdentity;

public class Server
{
    public static void main(String[] args)
    {
        try(Communicator communicator = initialize(args, "server.cfg"))
        {
            ObjectAdapter adapter = communicator.createObjectAdapter("Server");

            Object object = new ParkingService(new ArrayList<>());
            adapter.add(object, stringToIdentity("Parking"));

            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}