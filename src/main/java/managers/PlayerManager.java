package managers;

import daos.PlayerDAOImpl;
import entities.Player;
import exceptions.PlayerNotFoundException;
import utils.ValidateInputs;

import java.util.List;

public class PlayerManager {

    PlayerDAOImpl playerDAO = new PlayerDAOImpl();

    public Player createPlayer(String name) {
        String email = ValidateInputs.validateString("Enter a valid email: ");
        Player player = new Player(name, email);

        playerDAO.save(player);
        return player;
    }

    public Player selectOrCreatePlayer() {
        PlayerDAOImpl playerDAOImpl = new PlayerDAOImpl();
        List<Player> players = playerDAOImpl.findAll();
        String name = ValidateInputs.validateString("Enter the name of the player: ");
        for (Player player : players) {
            if (name.equals(player.getName())) {
                return player;
            }
        }
        return createPlayer(name);
    }

    public Player selectPlayer() throws PlayerNotFoundException {
        PlayerDAOImpl playerDAOImpl = new PlayerDAOImpl();
        List<Player> players = playerDAOImpl.findAll();
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
