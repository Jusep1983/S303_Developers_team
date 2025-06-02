package daos.interfaces;

import entities.Room;
import org.bson.types.ObjectId;

import java.util.List;

public interface RoomDAO {

    void save(Room room);
    List<Room> findAll();
    void update(Room room);
    void delete(Room room);
}
