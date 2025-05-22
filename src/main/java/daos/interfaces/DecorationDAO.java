package daos.interfaces;

import entities.Decoration;
import org.bson.types.ObjectId;

public interface DecorationDAO {
    void addDecoration(ObjectId roomId, Decoration decoration);
    void deleteDecoration(ObjectId roomId, Decoration decoration);
}
