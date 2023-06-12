import java.io.Serializable;
import java.time.LocalDate;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1;

    private String vehicleLicencePlate;

    private LocalDate entryDate;

    private LocalDate exitDate;

    public Vehicle(String vehicleLicencePlate, LocalDate entryDate, LocalDate exitDate){
        this.vehicleLicencePlate = vehicleLicencePlate;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public String getVehicleLicencePlate(){
        return vehicleLicencePlate;
    }

    public LocalDate getEntryDate(){
        return entryDate;
    }

    public LocalDate getExitDate(){
        return exitDate;
    }
}
