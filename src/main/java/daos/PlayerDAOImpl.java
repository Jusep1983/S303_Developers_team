package daos;

import com.mongodb.client.MongoCollection;
import daos.interfaces.PlayerDAO;
import database.MongoDBConnection;
import entities.Player;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO {

    private final MongoCollection<Document> playersCollection;

    public PlayerDAOImpl() {
        playersCollection = MongoDBConnection.getPlayersCollection();
    }

    @Override
    public void save(Player player) {
        Document doc = new Document(
                "_id", player.getId())
                .append("name", player.getName())
                .append("email", player.getEmail())
                .append("isSubscribed", player.isSubscribed());
        playersCollection.insertOne(doc);
    }

    @Override
    public Player findById(String id) {
        Document doc = playersCollection.find(new Document("_id", id)).first();
        return doc != null ? documentToPlayer(doc) : null;
    }

    @Override
    public List<Player> findAll() {
        List<Player> players = new ArrayList<>();
        for (Document doc : playersCollection.find()) {
            players.add(documentToPlayer(doc));
        }
        return players;
    }

    @Override
    public void update(Player player) {
        Document updated = new Document("name", player.getName())
                .append("email", player.getEmail())
                .append("isSubscribed", player.isSubscribed());
        playersCollection.updateOne(new Document("_id", player.getId()),
                new Document("$set", updated));
    }

    @Override
    public void delete(Player player) {
        playersCollection.deleteOne(new Document("_id", player.getId()));
    }

    private Player documentToPlayer(Document doc) {
        return new Player(
                doc.getObjectId("_id"),
                doc.getString("name"),
                doc.getString("email"),
                doc.getBoolean("isSubscribed")
        );
    }
}
