import com.zeroc.Ice.Current;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ParkingService implements ParkingDemo.ParkingFunctions{

    public final String PARKING_HISTORY_PATH = "./server/data/ParkingHistory";

    public ArrayList<Vehicle> parkingHistory;

    public Parking parking;

    public RollbackI rollbackI;

    private static ParkingService instance;

    public static ParkingService getInstance(ArrayList<Vehicle> parkingHistory){
        if(instance == null){
            instance = new ParkingService(parkingHistory);
        }
        return instance;
    }

    private ParkingService(ArrayList<Vehicle> parkingHistory) {
        this.parkingHistory = parkingHistory;
        parking = new Parking(this);
        rollbackI = new RollbackI(this);
    }

    public String parkingService(String licensePlate, Current current) {

        loadData(current);

        if(isInParking(licensePlate, current)){
            String enterDate = parking.getEnterDate(licensePlate, current);
            double hoursToPay = parking.calculateHours(enterDate, current);
            int valueToPay = (int) parking.payable(hoursToPay, current);

            setExitDate(licensePlate, String.valueOf(LocalDateTime.now()), current);
            rollbackI.generateFile(current);
            System.out.println("El vehiculo con placa "+licensePlate+" debe pagar $"+valueToPay);

            return "Total a pagar: $"+valueToPay+"\n";
        }

        System.out.println("Por favor ingrese la fecha de entrada del vehiculo "+licensePlate+" de la forma: anio-mes-dia HH:MM");
        String entryDate = getDate(current);

        parkingHistory.add(new Vehicle(licensePlate, entryDate, null));
        rollbackI.generateFile(current);

        return "Su vehiculo ingreso con exito al parquadero \n";
    }

    @Override
    public boolean isInParking(String licensePlate, Current current) {

        List<Vehicle> vehicles = parkingHistory.stream()
                .filter(vehicle -> vehicle.getVehicleLicencePlate().equalsIgnoreCase(licensePlate) &&
                        vehicle.getExitDate() == null)
                .collect(Collectors.toList());

        return vehicles.size() == 1;
    }

    @Override
    public void printString(String message, Current current) {
        System.out.println(message);
    }

    @Override
    public String getDate(Current current) {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void loadData(Current current) {
        File parkingHistoryFile = new File(PARKING_HISTORY_PATH);

        try {
            if (parkingHistoryFile.exists()){
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(parkingHistoryFile));
                parkingHistory = (ArrayList<Vehicle>)objectInputStream.readObject();
                objectInputStream.close();
            }
        }catch (Exception exception){
            System.out.println("No se pudo cargar los datos");
        }
    }

    @Override
    public void setExitDate(String licensePlate, String exitDate, Current current) {

        List<Vehicle> vehicles = parkingHistory.stream()
                .filter(vehicle -> vehicle.getVehicleLicencePlate().equalsIgnoreCase(licensePlate) &&
                        vehicle.getExitDate() == null)
                .collect(Collectors.toList());

        vehicles.get(0).setExitDate(exitDate);
    }

}
