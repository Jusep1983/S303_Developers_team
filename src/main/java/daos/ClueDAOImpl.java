package daos;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import daos.interfaces.ClueDAO;
import entities.Clue;
import entities.enums.Theme;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ClueDAOImpl implements ClueDAO {
    private final MongoCollection<Document> roomsMongoCollection;

    public ClueDAOImpl(MongoDatabase database) {
        roomsMongoCollection = database.getCollection("rooms");

    }

    @Override
    public void save(Clue clue, ObjectId roomId) {
        Document doc = new Document("_id", clue.getId())
                .append("price", clue.getPrice())
                .append("name", clue.getName())
                .append("theme", clue.getTheme());

        roomsMongoCollection.insertOne(doc);


    }

    @Override
    public void delete(ObjectId clueId, ObjectId roomId) {
        roomsMongoCollection.updateOne(
                new Document("_id", roomId),
                new Document("$pull", new Document("clues", new Document("_id", clueId)))
        );

    }

    @Override
    public Clue findById(ObjectId clueId, ObjectId roomId) {

        Document roomDoc = roomsMongoCollection
                .find(new Document("_id", roomId)).first();
        if (roomDoc == null) {
            return null;
        }
        List<Document> clues = (List<Document>) roomDoc.get("clues");

        for (Document clueDoc : clues) {
            ObjectId clueDocId = clueDoc.getObjectId("_id");
            if (clueDocId.equals(clueId))
                return new Clue(
                        clueDocId,
                        clueDoc.getInteger("price"),
                        clueDoc.getString("name"),
                        Theme.valueOf(clueDoc.getString("theme"))
                );


        }
        return null;


    }

    @Override
    public List<Clue> findAll(ObjectId roomId) {
        List<Clue> clues = new ArrayList<>();
        Document roomDoc = roomsMongoCollection
                .find(new Document("_id", roomId)).first();
        if (roomDoc == null) {
            return null;
        }
            List<Document> cluesdocList = (List<Document>) roomDoc.get("clues");


            for (Document clueDoc : cluesdocList) {
                clues.add(new Clue(
                        clueDoc.getObjectId("_id"),
                        clueDoc.getInteger("price"),
                        clueDoc.getString("name"),
                        Theme.valueOf(clueDoc.getString("theme"))
                ));

            }
            return clues;


        }


    }
