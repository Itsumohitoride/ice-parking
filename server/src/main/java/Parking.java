import com.zeroc.Ice.Current;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Parking implements ParkingDemo.TotalPayable {

    int costParking ;

    @Override
    public double getValueHours(Current current) {
        return 0;
    }

    @Override
    public double calculateHours(String enter, Current current) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        double cantidadHoras = 0;
        try {

            LocalDateTime dateEnter = LocalDateTime.parse(enter, formatter);
            LocalDateTime fechaActual = LocalDateTime.now();
            try {
                if (dateEnter.isAfter(fechaActual)) {
                    throw new Exception("La fecha ingresada o la hora es posterior a la actual");
                }
                Duration duracion = Duration.between(dateEnter, fechaActual);
                cantidadHoras = duracion.getSeconds();
                cantidadHoras = cantidadHoras / 3600;

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error: El formato de fecha y hora no es válido.");
        }
        return cantidadHoras;
    }

    @Override
    public double payable(double hours, Current current) {

        return hours*getValueHours(current);
    }
}
