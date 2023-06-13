import com.zeroc.Ice.Current;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Parking implements ParkingDemo.TotalPayable {

    public final int costParking = 1000;

    public ParkingService parkingService;

    public Parking(ParkingService parkingService){
        this.parkingService = parkingService;
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
                parkingService.printString("Error: " + e.getMessage(), current);
            }

        } catch (Exception e) {
            parkingService.printString("Error: El formato de fecha y hora no es valido.", current);
        }
        return cantidadHoras;
    }

    @Override
    public String getEnterDate(String licensePlate, Current current) {

        List<Vehicle> vehicles = parkingService.parkingHistory.stream()
                .filter(vehicle -> vehicle.getVehicleLicencePlate().equalsIgnoreCase(licensePlate))
                .collect(Collectors.toList());

        return vehicles.get(0).getEntryDate();
    }

    @Override
    public double payable(double hours, Current current) {
        return hours * getValueHours(current);
    }

    @Override
    public double getValueHours(Current current) {
        return costParking;
    }
}
