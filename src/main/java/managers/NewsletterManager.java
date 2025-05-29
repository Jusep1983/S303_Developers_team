package managers;

import daos.PlayerDAOImpl;
import entities.Newsletter;
import entities.Player;
import validation.ValidateInputs;

import java.util.List;

public class NewsletterManager {
    private List<Player> players;
    private final Newsletter newsletter;

    public NewsletterManager() {
        this.players = new PlayerDAOImpl().findAll();
        this.newsletter = new Newsletter(players);
    }

    public void unsubscribePlayer() {
        List<Player> playersUpdated = new PlayerDAOImpl().findAll();
        String emailToUnsubscribe = ValidateInputs.validateString("Enter the email of the player to unsubscribe: ");
        Player playerToUnsubscribe = playersUpdated.stream()
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
