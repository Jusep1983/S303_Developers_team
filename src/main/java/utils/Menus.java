package utils;

import database.MongoDBConnection;
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
                5.  Edit room (add/del clue and decoration)
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
