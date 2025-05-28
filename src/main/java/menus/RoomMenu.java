package menus;

import managers.ClueManager;
import managers.DecorationManager;
import validation.ValidateInputs;

public class RoomMenu {

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

    public static void subMenuEditManager(ClueManager clueManager, DecorationManager decorationManager) {
        boolean exit = false;
        do {
            switch (subMenuEditOptions()) {
                case 1:
                    clueManager.addClueToRoom();
                    break;
                case 2:
                    clueManager.deleteClueFromRoom();
                    break;
                case 3:
                    decorationManager.addDecorationToRoom();
                    break;
                case 4:
                    decorationManager.deleteDecorationFromRoom();
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
