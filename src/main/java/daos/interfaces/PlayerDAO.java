package daos.interfaces;

import dtos.PlayerDTO;
import entities.Player;

import java.util.List;

public interface PlayerDAO {

    void save(Player player);
    List<Player> findAll();
    List<PlayerDTO> findAllDTO();
    void update(Player player);
}
