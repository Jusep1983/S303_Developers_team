package application;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.InitialDataLoader;
import database.MongoDBConnection;

import org.bson.Document;
import utils.Menus;

import java.util.ArrayList;


public class ApplicationLauncher {

    public static void run() {
        // TODO: se implementará la carga de datos de la base de datos
        MongoDatabase database = MongoDBConnection.getInstance();

        String jsonFilePath = "src/main/java/database/datas/rooms.json";

        InitialDataLoader.loadInitialRoomsIfDatabaseIsEmpty(database, jsonFilePath);

//        MongoCollection<Document> roomsCollection = database.getCollection("rooms");
//        MongoCollection<Document> playersCollection = database.getCollection("players");

//        ArrayList<Document> roomList = roomsCollection.find().into(new ArrayList<>());
//        ArrayList<Document> playerList = playersCollection.find().into(new ArrayList<>());

        boolean exit = false;

        while (!exit) {
            switch (Menus.mainMenuOptions()) {
                case 1:
                    // TODO: mostrar inventario
                    System.out.println(">> Showing total inventory...");
                    break;
                case 2:
                    // TODO: calcular valor total
                    System.out.println(">> Calculating inventory value...");
                    break;
                case 3:
                    // TODO: lógica para añadir sala
                    System.out.println(">> Adding new room...");
                    break;
                case 4:
                    // TODO: lógica para eliminar sala
                    System.out.println(">> Deleting room...");
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
                    System.out.println("Exiting Escape Room App. Bye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
