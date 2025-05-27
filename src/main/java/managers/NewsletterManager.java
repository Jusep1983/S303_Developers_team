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
}
