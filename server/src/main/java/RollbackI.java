import com.zeroc.Ice.Current;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class RollbackI implements ParkingDemo.Rollback{

    public final String PARKING_HISTORY_PATH = "./server/data/ParkingHistory";

    public final String BACKUP_FILE = "./server/data/ParkingHistoryBackup";

    public ParkingService parkingService;

    public RollbackI(ParkingService parkingService) {
        this.parkingService = parkingService;

    }
    @Override
    public void generateFile(Current current) {
        Path originalFile = Path.of(PARKING_HISTORY_PATH);
        Path backupFile = Path.of(BACKUP_FILE);

        try {
            Files.copy(originalFile, backupFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copia de seguridad creada correctamente.");

        } catch (IOException e) {

            System.out.println("Error al crear la copia de seguridad: " + e.getMessage());

            try {
                Files.copy(backupFile, originalFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Se realizo backup con exito");
            }catch (Exception exception){
                System.out.println("Backup no disponible");
            }
        }
        modifyFile(current);
    }

    @Override
    public void modifyFile(Current current) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(PARKING_HISTORY_PATH));
            objectOutputStream.writeObject(parkingService.parkingHistory);
            objectOutputStream.close();

            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE));
            objectOutputStream2.writeObject(parkingService.parkingHistory);
            objectOutputStream2.close();

        }catch (Exception exception){
            System.out.println("Hubo una falla al guardar los datos del historial de parqueo");
        }
    }

}
