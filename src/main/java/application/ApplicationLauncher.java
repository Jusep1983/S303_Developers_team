package application;

import com.mongodb.client.MongoDatabase;
import database.InitialDataLoader;
import database.MongoDBConnection;

import menus.MainMenu;

public class ApplicationLauncher {

    public static void run() {
        MongoDatabase database = MongoDBConnection.getInstance();
        String jsonFilePath = "src/main/java/database/datas/rooms.json";
        InitialDataLoader.loadInitialRoomsIfDatabaseIsEmpty(database, jsonFilePath);

        MainMenu menu = new MainMenu();
        menu.mainMenuManager();
    }
}
