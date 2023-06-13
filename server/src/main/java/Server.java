import com.zeroc.Ice.Current;
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
            PingEchoI pingEchoI = new PingEchoI();
            System.out.println(pingEchoI.ping("10.147.19.125"));
            System.out.println(pingEchoI.ping("10.147.19.19"));

            ObjectAdapter adapter = communicator.createObjectAdapter("Server");

            Object object = ParkingService.getInstance(new ArrayList<>());
            adapter.add(object, stringToIdentity("Parking"));

            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}