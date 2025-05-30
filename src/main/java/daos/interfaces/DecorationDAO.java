package daos.interfaces;

import entities.Decoration;
import org.bson.types.ObjectId;

import java.util.List;

public interface DecorationDAO {
    void save(Decoration decoration, ObjectId roomId);
    void delete(ObjectId clueId, ObjectId roomId);
    List<Decoration> findAll(ObjectId roomId);
}
