package application;

import com.mongodb.client.MongoDatabase;
import daos.ClueDAOImpl;
import daos.PlayerDAOImpl;
import daos.RoomDAOImpl;
import daos.interfaces.ClueDAO;
import database.InitialDataLoader;
import database.MongoDBConnection;
import entities.Newsletter;
import entities.Player;
import managers.BusinessManager;
import managers.EscapeRoomManager;
import managers.RoomManager;
import managers.TicketPrinter;

import managers.*;

import utils.Menus;

import java.util.List;

public class ApplicationLauncher {

    public static void run() {
        MongoDatabase database = MongoDBConnection.getInstance();
        String jsonFilePath = "src/main/java/database/datas/rooms.json";
        InitialDataLoader.loadInitialRoomsIfDatabaseIsEmpty(database, jsonFilePath);

        // No sería mejor intentar hacer esto de otra forma ¿?
        // Aqui hay que darle una vuelta a esta forma de plantearlo... He añadido un comentario diver al commit de prueba!
        EscapeRoomManager escapeRoomManager = new EscapeRoomManager();
        RoomManager roomManager = new RoomManager();
        RoomDAOImpl roomDAOImpl = new RoomDAOImpl();
        BusinessManager businessManager = new BusinessManager();

        PlayerDAOImpl playerDAO = new PlayerDAOImpl();
        List<Player> players = playerDAO.findAll();
        Newsletter newsletter = new Newsletter(players);
        ClueDAOImpl clueDao = new ClueDAOImpl(database);

        

        ClueManager clueManager = new ClueManager(roomManager, clueDao);
        DecorationManager decorationManager = new DecorationManager(roomManager);
        Menus.mainMenuManager(escapeRoomManager, roomManager, roomDAOImpl, businessManager, newsletter, clueManager, decorationManager);

    }
}
