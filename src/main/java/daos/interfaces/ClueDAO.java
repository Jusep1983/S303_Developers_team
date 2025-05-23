package daos.interfaces;

import entities.Clue;
import entities.Room;
import org.bson.types.ObjectId;

import java.util.List;

public interface ClueDAO {
    void save(Clue clue, ObjectId roomId);

    void delete(ObjectId clueId, ObjectId roomId);

    Clue findById(ObjectId id, ObjectId roomId);

    List<Clue> findAll(ObjectId roomId);
}
    // igual se puede hacer una interfaz commmun para el CRUD de clue y decoration

//    public interface ClueDAO {
//        void save(T entity, ObjectId roomId);
//        void delete(T entity, ObjectId roomId);
//        Clue findById(ObjectId id, , ObjectId roomId);
//        List<T> findAll(ObjectId roomId);
//}

