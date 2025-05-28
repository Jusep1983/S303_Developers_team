package observer;

import daos.PlayerDAOImpl;
import entities.Newsletter;
import entities.Player;
import entities.Room;
import validation.ValidateInputs;

import java.util.List;

public class NotificationService {
    private final PlayerDAOImpl playerDAO;
    private final Newsletter newsletter;

    public NotificationService(PlayerDAOImpl playerDAO, Newsletter newsletter){
        this.playerDAO = playerDAO;
        this.newsletter = newsletter;
    }

//    public void notifyNewRoomToAllSubscribedPlayers(Room room) {
//        List<Player> players = playerDAO.findAll();
//        for (Player p : players) {
//            if (p.isSubscribed()) {
//                p.update("A new room has been created! Try our new " + room.getName());
//            }
//        }
//    }


    public void notifyAllPlayers() {
        String lastNewsToAll = ValidateInputs.validateString("Enter the message you want to send to all players : ");
        notifyAllPlayers(lastNewsToAll);
    }

    public void notifyAllPlayers(String lastNewsToAll) {
        List<Player> updatedPlayers = playerDAO.findAll();
        newsletter.setLastNews(lastNewsToAll);
        newsletter.getObservers().clear();
        updatedPlayers.forEach(newsletter::addObserver);
        newsletter.notifyObservers();
        System.out.println("Notification sent to all players!");
    }

    public void notifySubscribedPlayers() {
        String lastNewsToSubscribed = ValidateInputs.validateString("Enter the message you want to send to your subscribers : ");
        notifySubscribedPlayers(lastNewsToSubscribed);
    }

    public void notifySubscribedPlayers(String lastNewsToSubscribed){
        List<Player> updatedPlayers = playerDAO.findAll();
        newsletter.setLastNews(lastNewsToSubscribed);
        newsletter.getObservers().clear();
        updatedPlayers.stream().filter(Player::isSubscribed).forEach(newsletter::addObserver);
        newsletter.notifyObservers();
        System.out.println("Notification sent to subscribed players!");
    }
    public void notifyNewRoomToAllSubscribedPlayers(Room room) {
        notifySubscribedPlayers("A new room has been created! Try our new " + room.getName());
    }
}
