package observer;

import daos.PlayerDAOImpl;
import entities.Newsletter;
import entities.Player;
import entities.Room;
import utils.ValidateInputs;

import java.util.List;

public class RoomNotifier {
    private final PlayerDAOImpl playerDAO = new PlayerDAOImpl();
    private final List<Player> players = new PlayerDAOImpl().findAll();
    private final Newsletter newsletter = new Newsletter(new PlayerDAOImpl().findAll());

    public void notifyAllSubscribedPlayers(Room room) {
        List<Player> players = playerDAO.findAll();
        for (Player p : players) {
            if (p.isSubscribed()) {
                p.update("A new room has been created! Try our new " + room.getName());
            }
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
        System.out.println("Notification sent to subscribed players!");
    }
}
