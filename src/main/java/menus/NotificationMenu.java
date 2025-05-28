package menus;

import daos.PlayerDAOImpl;
import entities.Newsletter;
import entities.Player;
import observer.NotificationService;
import validation.ValidateInputs;

import java.util.List;

public class NotificationMenu {

    private final NotificationService notificationService;


    public NotificationMenu() {
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();
        List<Player> players = playerDAO.findAll();
        Newsletter newsletter = new Newsletter(players);
        this.notificationService = new NotificationService(playerDAO, newsletter);
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
                    notificationService.notifyAllPlayers();
                    break;
                case 2:
                    notificationService.notifySubscribedPlayers();
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
