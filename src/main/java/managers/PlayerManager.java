package managers;

import daos.PlayerDAOImpl;
import entities.Player;
import exceptions.PlayerNotFoundException;
import validation.ValidateInputs;

import java.util.List;

public class PlayerManager {

    PlayerDAOImpl playerDAO;

    public PlayerManager() {
        this.playerDAO = new PlayerDAOImpl();
    }

    public Player createPlayer(String name) {
        String email = ValidateInputs.validateEmail("Enter a valid email: ");
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
