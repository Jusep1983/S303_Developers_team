package daos.interfaces;

import entities.Player;

import java.util.List;

public interface PlayerDAO {

    void save(Player player);
    Player findById(String id);
    List<Player> findAll();
    void update(Player player);
    void delete(Player player);
}
