package managers;

import daos.PlayerDAOImpl;
import daos.RoomDAOImpl;
import daos.interfaces.RoomDAO;
import entities.Player;
import exceptions.PlayerNotFoundException;
import utils.ValidateInputs;

import java.util.List;

public class PlayerManager {

    PlayerDAOImpl playerDAO = new PlayerDAOImpl();
    private final RoomDAOImpl roomDAO = new RoomDAOImpl();


    public Player createPlayer(String name) {
        String email = ValidateInputs.validateString("Enter a valid email: ");
        Player player = new Player(name, email);

        playerDAO.save(player);
        return player;
    }

    public Player selectOrCreatePlayer() {
        List<Player> players = playerDAO.findAll();
        String name = ValidateInputs.validateString("Enter the name of the player: ");
        for (Player player : players) {
            if (name.equals(player.getName())) {
                return player;
            }
        }
        return createPlayer(name);
    }

    public Player selectPlayer() throws PlayerNotFoundException {
        List<Player> players = playerDAO.findAll();
        if (!players.isEmpty()) {
            String name = ValidateInputs.validateString("Enter the name of the player: ");
            for (Player player : players) {
                if (name.equals(player.getName())) {
                    return player;
                }
            }
        }
        throw new PlayerNotFoundException("this player never played");
    }
}
