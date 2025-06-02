package menus;

import database.MongoDBConnection;
import entities.EscapeRoom;
import entities.Player;
import entities.Room;
import managers.*;
import validation.ValidateInputs;

public class MainMenu {

    private static EscapeRoom escapeRoom;

    public MainMenu(EscapeRoom escapeRoom) {
        MainMenu.escapeRoom = escapeRoom;
    }

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

        Player player;
        boolean exit = false;

        while (!exit) {
            switch (MainMenu.mainMenuOptions()) {
                case 1:
                    System.out.println(">> Total inventory: ");
                    escapeRoom.escapeRoomManager().showAllAssets();
                    System.out.println(escapeRoom.escapeRoomManager().getInventoryCount());
                    break;
                case 2:
                    System.out.println(">> The total value of all escape room assets is €"
                                       + escapeRoom.escapeRoomManager().getInventoryValue()
                    );
                    break;
                case 3:
                    Room newRoom = RoomManager.createRoom();
                    escapeRoom.roomDAO().save(newRoom);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println(">> New room '" + newRoom.getName() + "' successfully added");
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    break;
                case 4:
                    escapeRoom.roomManager().deleteRoomByUserSelection();
                    break;
                case 5:
                    RoomMenu.subMenuEditManager(escapeRoom.clueManager(), escapeRoom.decorationManager());
                    break;
                case 6:
                    player = escapeRoom.playerManager().selectOrCreatePlayer();
                    escapeRoom.businessManager().processSale(player);
                    break;
                case 7:
                    System.out.println(">> Total sales: " + escapeRoom.ticketDAO().getTotalSales());
                    break;
                case 8:
                    escapeRoom.notificationMenu().notificationMenuManager();
                    break;
                case 9:
                    escapeRoom.newsletterManager().unsubscribePlayer();
                    break;
                case 10:
                    escapeRoom.certificatePrinter().processCertificate(escapeRoom);
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
