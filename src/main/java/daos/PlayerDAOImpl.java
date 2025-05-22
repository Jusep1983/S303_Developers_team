package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import daos.interfaces.PlayerDAO;
import database.MongoDBConnection;
import entities.Player;
import org.bson.types.ObjectId;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO {

    private final MongoCollection<Document> playersMongoCollection;

    public PlayerDAOImpl() {
        // Implementar metodo que devuelva la base de datos en MongoDBConnection
        MongoDatabase database = MongoDBConnection.getDatabase();
        playersMongoCollection = database.getCollection("players");
    }

    @Override
    public void save(Player player) {
        Document doc = new Document(
                "_id", player.getId())
                .append("name", player.getName())
                .append("email", player.getEmail())
                .append("isSubscribed", player.isSubscribed());
        playersMongoCollection.insertOne(doc);
    }

    @Override
    public Player findById(String id) {
        Document doc = playersMongoCollection.find(new Document("_id", id)).first();
        return doc != null ? documentToPlayer(doc) : null;
    }

    @Override
    public List<Player> findAll() {
        List<Player> players = new ArrayList<>();
        for (Document doc : playersMongoCollection.find()) {
            players.add(documentToPlayer(doc));
        }
        return players;
    }

    @Override
    public void update(Player player) {
        Document updated = new Document("name", player.getName())
                .append("email", player.getEmail())
                .append("isSubscribed", player.isSubscribed());
        playersMongoCollection.updateOne(new Document("_id", player.getId()),
                new Document("$set", updated));
    }

    @Override
    public void delete(Player player) {
        playersMongoCollection.deleteOne(new Document("_id", player.getId()));
    }

    private Player documentToPlayer(Document document) {
        return new Player(
                document.getObjectId("_id"),
                document.getString("name"),
                document.getString("email"),
                document.getBoolean("isSubscribed")
        );
    }
}
