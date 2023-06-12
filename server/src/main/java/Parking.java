import ParkingDemo.TotalPayable;
import com.zeroc.Ice.Current;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Parking implements TotalPayable {

    public final String PARKING_HISTORY_PATH = "./server/data/ParkingHistory";

    public final int costParking = 0;

    public ArrayList<Vehicle> parkingHistory;

    public Parking(ArrayList<Vehicle> parkingHistory) {
        this.parkingHistory = parkingHistory;
    }

    @Override
    public String parkingService(String licensePlate, Current current) {

        loadData();

        if(isInParking(licensePlate, current)){
            String enterDate = getEnterDate(licensePlate, current);
            return "Total payable: " + calculateHours(enterDate, current);
        }

        parkingHistory.add(new Vehicle(licensePlate, LocalDate.now(), null));
        saveData();

        int total = (int) payable(calculateHours(getDate(),current), current);
        String message = "Total payable for " + licensePlate +": " + total;
        printString(message, current);
        return message;
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
            printString("Error: El formato de fecha y hora no es válido.", current);
        }
        return cantidadHoras;
    }

    @Override
    public boolean isInParking(String licensePlate, Current current) {
        return false;
    }

    @Override
    public String getEnterDate(String licensePlate, Current current) {
        return null;
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
        return 5000;
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
            System.out.println(exception.getMessage());
            System.out.println("Hubo una falla al cargar los datos del historial de parqueo");
        }

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
