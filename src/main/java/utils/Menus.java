package utils;

import daos.RoomDAOImpl;
import database.MongoDBConnection;
import entities.Player;
import entities.Room;
import managers.BusinessManager;
import managers.CertificatePrinter;
import managers.EscapeRoomManager;
import managers.RoomManager;

public class Menus {
    // Menu provisional
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
                6.  Generate tickets (elegir sala, ++€ totalSales)
                7.  Show total sales
                ---Users options--------
                8.  Notifications
                9.  Unsubscribe players
                10. Generate certificate
                0.  Exit
                Please enter a valid option number (0–10):
                """, 0, 10);
    }

    public static int subMenuEditOptions() {
        return ValidateInputs.validateIntegerBetweenOnRange("""
                
                EDITOR SUBMENU
                1. Add Clue
                2. Delete Clue
                3. Add Decoration
                4. Delete Decoration
                0. Return to the Main Menú
                Please enter a valid option number (0–4):
                """, 0, 4);
    }

    public static void mainMenuManager(EscapeRoomManager escapeRoomManager, RoomManager roomManager, RoomDAOImpl roomDAOImpl, BusinessManager businessManager) {
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
                    Room newRoom = RoomManager.createRoom();
                    roomDAOImpl.save(newRoom);
                    System.out.println(">> New room '" + newRoom.getName() + "' successfully added");
                    break;
                case 4:
                    roomManager.deleteRoomByUserSelection();
                    break;
                case 5:
                    // TODO: añadir o quitar pista/decoración
                    subMenuEditManager(roomManager);
                    System.out.println(">> Editing room...");
                    break;
                case 6:
                    Player player = businessManager.createPlayer();
                    businessManager.processSale(player);
                    System.out.println(">> Generating ticket...");
                    break;
                case 7:
                    System.out.println(">> Total sales: " + businessManager.getTotalSales());
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
                    // TODO: decidir cómo se da certificado
                    // CertificatePrinter.printCertificate(player, room);
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

    public static void subMenuEditManager(RoomManager roomManager) {
        boolean exit = false;
        do {
            switch (subMenuEditOptions()) {
                case 1:
                    roomManager.addClueToRoom();
                    break;
                case 2:
                    roomManager.deleteClueFromRoom();
                    break;
                case 3:
                    roomManager.addDecorationToRoom();
                    break;
                case 4:
                    roomManager.deleteDecorationFromRoom();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Going back...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (!exit);
    }


}
