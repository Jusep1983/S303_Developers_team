package observer;

import daos.PlayerDAOImpl;
import entities.Player;
import entities.Room;

import java.util.List;

public class RoomNotifier {
    private final PlayerDAOImpl playerDAO = new PlayerDAOImpl();

    public void notifyAllSubscribedPlayers(Room room) {
        List<Player> players = playerDAO.findAll();
        for (Player p : players) {
            if (p.isSubscribed()) {
                p.update("A new room has been created! Try our new " + room.getName());
            }
        }
    }
}
