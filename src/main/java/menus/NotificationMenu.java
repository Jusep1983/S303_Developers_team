package menus;

import daos.PlayerDAOImpl;
import entities.Player;
import observer.RoomNotifier;
import validation.ValidateInputs;

import java.util.List;

public class NotificationMenu {

    List<Player> players;
    private final RoomNotifier roomNotifier;

    public NotificationMenu() {
        players = new PlayerDAOImpl().findAll();
        this.roomNotifier = new RoomNotifier();
    }


    public static int notificationMenuOptions() {
        return ValidateInputs.validateIntegerBetweenOnRange("""
           
           Choose an option :
            1. Send notification to all players
            2. Send notification to subscribed players
            0. Back
            
            """, 0, 2);
    }

    public void notificationMenuManager() {
        boolean exit = false;

        do {
            switch (notificationMenuOptions()) {
                case 1:
                    roomNotifier.notifyAllPlayers();
                    break;
                case 2:
                    roomNotifier.notifySubscribedPlayers();
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
