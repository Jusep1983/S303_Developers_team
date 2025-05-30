package daos.interfaces;

import entities.Clue;
import org.bson.types.ObjectId;

import java.util.List;

public interface ClueDAO {
    void save(Clue clue, ObjectId roomId);
    void delete(ObjectId clueId, ObjectId roomId);
    List<Clue> findAll(ObjectId roomId);
}