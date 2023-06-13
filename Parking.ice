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
        double payable(double hours);
        double getValueHours();
        double calculateHours(string enter);
        string getEnterDate(string licensePlate);
    }

    interface ParkingFunctions
    {
        string parkingService(string licensePlate);
        bool isInParking(string licensePlate);
        void printString(string message);
        string getDate();
        void loadData();
        void setExitDate(string date, string exitDate);
    }
}