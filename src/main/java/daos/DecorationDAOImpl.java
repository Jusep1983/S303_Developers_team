package daos;

import com.mongodb.client.MongoCollection;
import daos.interfaces.DecorationDAO;
import database.MongoDBConnection;
import entities.Clue;
import entities.Decoration;
import entities.enums.Theme;
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
    public Decoration findById(ObjectId decorationId, ObjectId roomId) {

        Document roomDoc = escapeRoomCollection
                .find(new Document("_id", roomId)).first();
        if (roomDoc == null) {
            return null;
        }
        List<Document> decorations = (List<Document>) roomDoc.get("decorations");

        for (Document decorationDoc : decorations) {
            ObjectId decorationDocId = decorationDoc.getObjectId("_id");
            if (decorationDocId.equals(decorationId))
                return new Decoration(
                        decorationDocId,
                        decorationDoc.getInteger("price"),
                        decorationDoc.getString("name"),
                        Decoration.Material.valueOf(decorationDoc.getString("material"))
                );
        }
        return null;
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
                    Decoration.Material.valueOf(decorationDoc.getString("material"))
            ));
        }
        return decorations;
    }
}
