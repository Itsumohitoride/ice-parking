import ParkingDemo.TotalPayable;
import com.zeroc.Ice.Current;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Parking implements TotalPayable {

    public final String PARKING_HISTORY_PATH = "./server/data/ParkingHistory";

    public final int costParking = 1000;

    public ArrayList<Vehicle> parkingHistory;

    public Parking(ArrayList<Vehicle> parkingHistory) {
        this.parkingHistory = parkingHistory;
    }

    @Override
    public String parkingService(String licensePlate, Current current) {

        loadData();

        if(isInParking(licensePlate, current)){
            String enterDate = getEnterDate(licensePlate, current);
            double hoursToPay = calculateHours(enterDate, current);
            int valueToPay = (int) payable(hoursToPay, current);

            setExitDate(licensePlate, LocalDateTime.now());
            saveData();
            System.out.println("El vehiculo con placa "+licensePlate+" debe pagar $"+valueToPay);

            return "Total a pagar: $"+valueToPay+"\n";
        }

        System.out.println("Por favor ingrese la fecha de entrada del vehiculo "+licensePlate+" de la forma: anio-mes-dia HH:MM");
        String entryDate = getDate();

        parkingHistory.add(new Vehicle(licensePlate, entryDate, null));
        saveData();

        return "Su vehiculo ingreso con exito al parquadero \n";
    }

    @Override
    public double calculateHours(String enter, Current current) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        double cantidadHoras = 0;
        try {
            LocalDateTime dateEnter = LocalDateTime.parse(enter, formatter);
            LocalDateTime dateActual = LocalDateTime.now();
            try {
                if (dateEnter.isAfter(dateActual)) {
                    throw new Exception("La fecha ingresada o la hora es posterior a la actual");
                }
                Duration duration = Duration.between(dateEnter, dateActual);
                cantidadHoras = duration.getSeconds();
                cantidadHoras = cantidadHoras / 3600;

            } catch (Exception e) {
                printString("Error: " + e.getMessage(), current);
            }

        } catch (Exception e) {
            printString("Error: El formato de fecha y hora no es valido.", current);
        }
        return cantidadHoras;
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
    public String getEnterDate(String licensePlate, Current current) {

        List<Vehicle> vehicles = parkingHistory.stream()
                .filter(vehicle -> vehicle.getVehicleLicencePlate().equalsIgnoreCase(licensePlate))
                .collect(Collectors.toList());

        return vehicles.get(0).getEntryDate();
    }

    @Override
    public void printString(String message, Current current) {
        System.out.println(message);
    }

    @Override
    public double payable(double hours, Current current) {
        return hours * getValueHours(current);
    }

    @Override
    public double getValueHours(Current current) {
        return costParking;
    }

    public String getDate(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void loadData() {

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

    public void setExitDate(String licensePlate, LocalDateTime exitDate){

        List<Vehicle> vehicles = parkingHistory.stream()
                .filter(vehicle -> vehicle.getVehicleLicencePlate().equalsIgnoreCase(licensePlate))
                .collect(Collectors.toList());

        vehicles.get(0).setExitDate(String.valueOf(exitDate));
    }

    public void saveData(){

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(PARKING_HISTORY_PATH));
            objectOutputStream.writeObject(parkingHistory);
            objectOutputStream.close();

        }catch (Exception exception){
            System.out.println("Hubo una falla al guardar los datos del historial de parqueo");
        }
    }
}
