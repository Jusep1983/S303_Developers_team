package daos.interfaces;

import entities.Player;
import org.bson.types.ObjectId;

import java.util.List;

public interface PlayerDAO {

    void save(Player player);
    Player findById(ObjectId id);
    List<Player> findAll();
    void update(Player player);
    void delete(Player player);
}
