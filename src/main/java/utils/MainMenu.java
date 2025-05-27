package utils;

import daos.RoomDAOImpl;
import database.MongoDBConnection;
import entities.Player;
import entities.Room;
import managers.*;
import managers.NewsletterManager;



public class MainMenu {

    private final EscapeRoomManager escapeRoomManager = new EscapeRoomManager();
    private final RoomManager roomManager = new RoomManager();
    private final RoomDAOImpl roomDAOImpl = new RoomDAOImpl();
    private final BusinessManager businessManager = new BusinessManager();
    private final ClueManager clueManager = new ClueManager(roomManager);
    private final DecorationManager decorationManager = new DecorationManager(roomManager);


    public static int mainMenuOptions() {
        return ValidateInputs.validateIntegerBetweenOnRange(""" 
                
                MAIN ESCAPE ROOM MENÚ
                ---Editor options-------
                1.  Show total inventory
                2.  Show total inventory value €
                3.  Add room
                4.  Delete room
                5.  Edit room (add/del clue or decoration)
                ---Business options-----
                6.  Venta tickets (elegir sala, ++€ totalSales)
                7.  Show total sales
                ---Users options--------
                8.  Notifications
                9.  Unsubscribe players
                10. Generate certificate
                0.  Exit
                Please enter a valid option number (0–10):
                """, 0, 10);
    }

    public void mainMenuManager() {

        RoomMenu roomMenu = new RoomMenu();
        NewsletterManager newsletterManager = new NewsletterManager();
        NotificationMenu notificationMenu = new NotificationMenu();

        boolean exit = false;

        while (!exit) {
            switch (MainMenu.mainMenuOptions()) {
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
                    Room newRoom = RoomManager.createRoom();
                    roomDAOImpl.save(newRoom);
                    System.out.println(">> New room '" + newRoom.getName() + "' successfully added");
                    break;
                case 4:
                    roomManager.deleteRoomByUserSelection();
                    break;
                case 5:
                    roomMenu.subMenuEditManager(clueManager, decorationManager);
                    break;
                case 6:
                    Player player = businessManager.selectPlayer();
                    businessManager.processSale(player);
                    break;
                case 7:
                    System.out.println(">> Total sales: " + businessManager.getTotalSales());
                    break;
                case 8:
                    notificationMenu.notificationMenuManager();
                    break;
                case 9:
                    newsletterManager.unsubscribePlayer();
                    System.out.println(">> Unsubscribing players...");
                    break;
                case 10:
                    // TODO: decidir cómo se da certificado
                    // CertificatePrinter printer = new CertificatePrinter();
                    // printer.printCertificate(player, room);
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
