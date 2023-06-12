import com.zeroc.Ice.Current;

public class RollbackI implements ParkingDemo.Rollback{
    @Override
    public boolean checkResourceAvailability(String ipAddress, Current current) {
        return false;
    }

    @Override
    public boolean performRollback(Current current) {
        return false;
    }
}
