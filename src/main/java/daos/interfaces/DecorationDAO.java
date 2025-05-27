package daos.interfaces;

import entities.Clue;
import entities.Decoration;
import org.bson.types.ObjectId;

import java.util.List;

public interface DecorationDAO {
    void addDecoration(ObjectId roomId, Decoration decoration);
    void deleteDecoration(ObjectId roomId, Decoration decoration);

    void save(Clue clue, ObjectId roomId);

    void delete(ObjectId clueId, ObjectId roomId);

    Clue findById(ObjectId id, ObjectId roomId);

    List<Clue> findAll(ObjectId roomId);
//    void save(Decoration decoration, ObjectId roomId);
//
//    void delete(ObjectId clueId, ObjectId roomId);
//
//    Decoration findById(ObjectId id, ObjectId roomId);
//
//    List<Decoration> findAll(ObjectId roomId);
}
