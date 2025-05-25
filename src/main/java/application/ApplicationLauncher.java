package application;

import com.mongodb.client.MongoDatabase;
import daos.RoomDAOImpl;
import database.InitialDataLoader;
import database.MongoDBConnection;
import managers.EscapeRoomManager;
import managers.RoomManager;
import utils.Menus;

public class ApplicationLauncher {

    public static void run() {
        MongoDatabase database = MongoDBConnection.getInstance();
        String jsonFilePath = "src/main/java/database/datas/rooms.json";
        InitialDataLoader.loadInitialRoomsIfDatabaseIsEmpty(database, jsonFilePath);
        EscapeRoomManager escapeRoomManager = new EscapeRoomManager();
        RoomManager roomManager = new RoomManager();
        RoomDAOImpl roomDAOImpl = new RoomDAOImpl();
        boolean exit = false;

        while (!exit) {
            switch (Menus.mainMenuOptions()) {
                case 1:
                    System.out.println(">> Total inventory: ");
                    escapeRoomManager.showAllAssets();
                    System.out.println(escapeRoomManager.getInventoryCount());
                    break;
                case 2:
                    System.out.println(">> The total value of all escape room assets is €"
                                       + escapeRoomManager.getInventoryValue()
                    );
                    break;
                case 3:
                    roomDAOImpl.save(RoomManager.createRoom());
                    System.out.println(">> New room '" + RoomManager.createRoom().getName() + "' successfully added");
                    break;
                case 4:
                    roomManager.deleteRoomByUserSelection();
                    break;
                case 5:
                    // TODO: añadir o quitar pista/decoración
                    System.out.println(">> Editing room...");
                    break;
                case 6:
                    // TODO: elegir sala, sumar a ventas
                    System.out.println(">> Generating ticket...");
                    break;
                case 7:
                    // TODO: mostrar ventas totales
                    System.out.println(">> Showing total sales...");
                    break;
                case 8:
                    // TODO: mostrar/gestionar notificaciones
                    System.out.println(">> Opening notifications panel...");
                    break;
                case 9:
                    // TODO: eliminar jugadores
                    System.out.println(">> Unsubscribing players...");
                    break;
                case 10:
                    // TODO: crear certificado
                    System.out.println(">> Generating certificate...");
                    break;
                case 0:
                    exit = true;
                    MongoDBConnection.close();
                    System.out.println("Exiting Escape Room App. Bye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
