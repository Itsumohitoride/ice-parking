import com.zeroc.Ice.Current;

public class PingEchoI implements Demo.PingEcho{
    @Override
    public String ping(String ipAddress, Current current) {
        return null;
    }
}
