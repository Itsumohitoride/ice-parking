module ParkingDemo
{
    interface Printer
    {
        void printString(string s);
    }

    interface PingEcho
    {
        string ping(string ipAddress);
    }

    interface Rollback
    {
        bool checkResourceAvailability(string ipAddress);
        bool performRollback();
    }
}