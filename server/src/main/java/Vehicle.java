import java.io.Serializable;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1;

    private String vehicleLicencePlate;

    private String entryDate;

    private String exitDate;

    public Vehicle(String vehicleLicencePlate, String entryDate, String exitDate){
        this.vehicleLicencePlate = vehicleLicencePlate;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public String getVehicleLicencePlate(){
        return vehicleLicencePlate;
    }

    public String getEntryDate(){
        return entryDate;
    }

    public String getExitDate(){
        return exitDate;
    }

    public void setExitDate(String exitDate){
        this.exitDate = exitDate;
    }
}
