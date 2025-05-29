package application;

import com.mongodb.client.MongoDatabase;
import database.InitialDataLoader;
import database.MongoDBConnection;

import menus.MainMenu;

public class ApplicationLauncher {

    public static void run() {
        MongoDatabase database = MongoDBConnection.getInstance();

        String roomsJsonPath = "src/main/java/database/datas/rooms.json";
        String playersJsonPath = "src/main/java/database/datas/players.json";
        InitialDataLoader.loadInitialRoomsIfDatabaseIsEmpty(database, roomsJsonPath, playersJsonPath);

        MainMenu menu = new MainMenu();
        menu.mainMenuManager();
    }
}
