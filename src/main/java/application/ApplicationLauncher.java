package application;

import com.mongodb.client.MongoDatabase;
import daos.RoomDAOImpl;
import database.InitialDataLoader;
import database.MongoDBConnection;
import managers.*;
import utils.Menus;

public class ApplicationLauncher {

    public static void run() {
        MongoDatabase database = MongoDBConnection.getInstance();
        String jsonFilePath = "src/main/java/database/datas/rooms.json";
        InitialDataLoader.loadInitialRoomsIfDatabaseIsEmpty(database, jsonFilePath);

        // No sería mejor intentar hacer esto de otra forma ¿?
        EscapeRoomManager escapeRoomManager = new EscapeRoomManager();
        RoomManager roomManager = new RoomManager();
        RoomDAOImpl roomDAOImpl = new RoomDAOImpl();
        BusinessManager businessManager = new BusinessManager();
        ClueManager clueManager = new ClueManager(roomManager);
        Menus.mainMenuManager(escapeRoomManager, roomManager, roomDAOImpl, businessManager, clueManager);
    }
}
