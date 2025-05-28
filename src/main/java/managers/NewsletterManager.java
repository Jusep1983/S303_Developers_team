package managers;

import daos.PlayerDAOImpl;
import entities.Newsletter;
import entities.Player;
import utils.ValidateInputs;

import java.util.List;

public class NewsletterManager {
    List<Player> players;
    private Newsletter newsletter;

    public NewsletterManager() {
        players = new PlayerDAOImpl().findAll();
        this.newsletter = new Newsletter(players);
    }

    public void unsubscribePlayer(){
        String emailToUnsubscribe = ValidateInputs.validateString("Enter the email of the player to unsubscribe: ");
        Player playerToUnsubscribe = players.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(emailToUnsubscribe))
                .findFirst()
                .orElse(null);

        if (playerToUnsubscribe != null) {
            if (!playerToUnsubscribe.isSubscribed()) {
                System.out.println("This player is already unsubscribed.");
            } else {
                playerToUnsubscribe.setSubscribed(false);
                newsletter.removeObserver(playerToUnsubscribe);
                new PlayerDAOImpl().update(playerToUnsubscribe);
                System.out.println(playerToUnsubscribe.getName() + " has been unsubscribed.");
            }
        } else {
            System.out.println("Player not found.");
        }
    }

    public void notifyAllPlayers() {
        String lastNewsToAll = ValidateInputs.validateString("Enter the message you want to send to all players : ");
        newsletter.setLastNews(lastNewsToAll);
        newsletter.getObservers().clear();
        for (Player player : players) {
            newsletter.addObserver(player);
        }
        newsletter.notifyObservers();
        //newsletter.notifyAllPlayers();
        System.out.println("Notification sent to all players!");
    }

    public void notifySubscribedPlayers() {
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
    }
}
