package utils;

import daos.PlayerDAOImpl;
import entities.Newsletter;
import entities.Player;

import java.util.List;

public class NotificationMenu {

    List<Player> players;
    private Newsletter newsletter;

    public NotificationMenu() {
        players = new PlayerDAOImpl().findAll();
        this.newsletter = new Newsletter(players);
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
                    String lastNewsToAll = ValidateInputs.validateString("Enter the message you want to send to all players : ");
                    newsletter.setLastNews(lastNewsToAll);
                    newsletter.getObservers().clear();
                    for (Player player : players) {
                        newsletter.addObserver(player);
                    }
                    newsletter.notifyObservers();
                    //newsletter.notifyAllPlayers();
                    System.out.println("Notification sent to all players!");
                    break;
                case 2:
                    String lastNewsToSubscribed = ValidateInputs.validateString("Enter the message you want to send to your subscribers : ");
                    newsletter.setLastNews(lastNewsToSubscribed);
                    newsletter.getObservers().clear();
                    for (Player player : players) {
                        if (player.isSubscribed())
                            newsletter.addObserver(player);
                    }
                    newsletter.notifyObservers();
                    //newsletter.notifySubscribed();
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
