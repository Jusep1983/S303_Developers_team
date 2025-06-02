package daos;

import com.mongodb.client.MongoCollection;
import daos.interfaces.DecorationDAO;
import database.MongoDBConnection;
import entities.Decoration;
import entities.enums.Material;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class DecorationDAOImpl implements DecorationDAO {

    private final MongoCollection<Document> escapeRoomCollection;

    public DecorationDAOImpl() {
        escapeRoomCollection = MongoDBConnection.getEscapeRoomCollection();
    }

    @Override
    public void save(Decoration decoration, ObjectId roomId) {
        Document doc = new Document("_id", decoration.getId())
                .append("price", decoration.getPrice())
                .append("name", decoration.getName())
                .append("theme", decoration.getMaterial());

        escapeRoomCollection.updateOne(
                new Document("_id", roomId),
                new Document("$push", new Document("decorations", doc))
        );
    }

    @Override
    public void delete(ObjectId decorationId, ObjectId roomId) {
        escapeRoomCollection.updateOne(
                new Document("_id", roomId),
                new Document("$pull", new Document("decorations", new Document("_id", decorationId)))
        );
    }

    @Override
    public List<Decoration> findAll(ObjectId roomId) {
        List<Decoration> decorations = new ArrayList<>();
        Document roomDoc = escapeRoomCollection
                .find(new Document("_id", roomId)).first();
        if (roomDoc == null) {
            return null;
        }
        List<Document> decorationsDocList = (List<Document>) roomDoc.get("decorations");

        for (Document decorationDoc : decorationsDocList) {
            decorations.add(new Decoration(
                    decorationDoc.getObjectId("_id"),
                    decorationDoc.getInteger("price"),
                    decorationDoc.getString("name"),
                    Material.valueOf(decorationDoc.getString("material"))
            ));
        }
        return decorations;
    }
}
