package daos;

import com.mongodb.client.MongoCollection;
import daos.interfaces.DecorationDAO;
import database.MongoDBConnection;
import entities.Decoration;
import org.bson.Document;
import org.bson.types.ObjectId;

public class DecorationDAOImpl implements DecorationDAO {
    private final MongoCollection<Document> escapeRoomCollection;

    public DecorationDAOImpl() {
        escapeRoomCollection = MongoDBConnection.getEscapeRoomCollection();
    }

    @Override
    public void addDecoration(ObjectId roomId, Decoration decoration) {
        if (decoration.getId() == null) {
            decoration.setId(new ObjectId());
        }

        Document decorationDoc = new Document("_id", decoration.getId())
                .append("name", decoration.getName())
                .append("price", decoration.getPrice())
                .append("material", decoration.getMaterial().name());

        escapeRoomCollection.updateOne(
                new Document("_id", roomId),
                new Document("$push", new Document("decorations", decorationDoc))
        );

    }

    @Override
    public void deleteDecoration(ObjectId roomId, Decoration decoration) {
        escapeRoomCollection.updateOne(
                new Document("_id", roomId),
                new Document("$pull", new Document("decorations",
                        new Document("_id", decoration.getId())))
        );
    }

}
