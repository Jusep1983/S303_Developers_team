package menus;

import database.MongoDBConnection;
import dtos.RoomDTO;
import entities.EscapeRoom;
import entities.Player;
import entities.Room;
import exceptions.PlayerNotFoundException;
import managers.*;
import managers.NewsletterManager;
import validation.ValidateInputs;


public class MainMenu {

    private final EscapeRoom escapeRoom = EscapeRoom.getEscapeRoom();

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
                6.  Venta tickets
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

        NewsletterManager newsletterManager = new NewsletterManager();
        NotificationMenu notificationMenu = new NotificationMenu();
        Player player;
        boolean exit = false;

        while (!exit) {
            switch (MainMenu.mainMenuOptions()) {
                case 1:
                    System.out.println(">> Total inventory: ");
                    escapeRoom.getEscapeRoomManager().showAllAssets();
                    System.out.println(escapeRoom.getEscapeRoomManager().getInventoryCount());
                    break;
                case 2:
                    System.out.println(">> The total value of all escape room assets is €"
                                       + escapeRoom.getEscapeRoomManager().getInventoryValue()
                    );
                    break;
                case 3:
                    Room newRoom = RoomManager.createRoom();
                    escapeRoom.getRoomDAO().save(newRoom);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println(">> New room '" + newRoom.getName() + "' successfully added");
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    break;
                case 4:
                    escapeRoom.getRoomManager().deleteRoomByUserSelection();
                    break;
                case 5:
                    RoomMenu.subMenuEditManager(escapeRoom.getClueManager(), escapeRoom.getDecorationManager());
                    break;
                case 6:
                    player = escapeRoom.getPlayerManager().selectOrCreatePlayer();
                    escapeRoom.getBusinessManager().processSale(player);
                    break;
                case 7:
                    System.out.println(">> Total sales: " + escapeRoom.getTicketDAO().getTotalSales());
                    break;
                case 8:
                    notificationMenu.notificationMenuManager();
                    break;
                case 9:
                    newsletterManager.unsubscribePlayer();
                    break;
                case 10:
                    CertificatePrinter printer = new CertificatePrinter();
                    try {
                        player = escapeRoom.getPlayerManager().selectPlayer();
                        RoomDTO room = escapeRoom.getRoomManager().getRoomDTO("print certification for");
                        printer.printCertificate(player, room);

                    } catch (PlayerNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
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
