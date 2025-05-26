package managers;

import daos.PlayerDAOImpl;
import entities.Newsletter;
import entities.Player;
import utils.ValidateInputs;

import java.util.List;

import static utils.Menus.notificationMenuOptions;

public class NewsletterManager {
    List<Player> players = new PlayerDAOImpl().findAll();
    private Newsletter newsletter;

    public NewsletterManager() {
        List<Player> players = new PlayerDAOImpl().findAll();
        this.newsletter = new Newsletter(players);
    }

    public void notificationMenuManager() {
        boolean exit = false;


        do {
            switch (notificationMenuOptions()) {
                case 1:
                    String lastNewsToAll = ValidateInputs.validateString("Enter the message you want to send to all players : ");
                    newsletter.setLastNews(lastNewsToAll);
                    newsletter.notifyAllPlayers();
                    System.out.println("Notification sent to all players!");
                    break;
                case 2:
                    String lastNewsToSubscribed = ValidateInputs.validateString("Enter the message you want to send to your subscribers : ");
                    newsletter.setLastNews(lastNewsToSubscribed);
                    newsletter.notifySubscribed();
                    System.out.println("Notification sent to subscribed players!");
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
