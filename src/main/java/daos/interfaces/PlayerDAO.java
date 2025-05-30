package daos.interfaces;

import entities.Player;

import java.util.List;

public interface PlayerDAO {

    void save(Player player);
    List<Player> findAll();
    void update(Player player);
}
