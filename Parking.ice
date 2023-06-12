module ParkingDemo
{
    interface PingEcho
    {
        string ping(string ipAddress);
    }

    interface Rollback
    {
        bool checkResourceAvailability(string ipAddress);
        bool performRollback();
    }

    interface TotalPayable
    {
        string parkingService(string licensePlate);
        double payable(double hours);
        double getValueHours();
        double calculateHours(string enter);
        bool isInParking(string licensePlate);
        string getEnterDate(string licensePlate);
        void printString(string message);
    }
}