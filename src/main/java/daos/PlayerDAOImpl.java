package daos;

import com.mongodb.client.MongoCollection;
import daos.interfaces.PlayerDAO;
import database.MongoDBConnection;
import entities.Player;
import entities.Ticket;
import org.bson.Document;
import org.bson.types.ObjectId;

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
    public Player findById(ObjectId id) {
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
        List<Document> ticketDocs = player.getTicketsBought().stream().map(TicketDAOImpl::ticketToDocument).toList();
        Document updated = new Document("$set", new Document("name", player.getName())
                .append("email", player.getEmail())
                .append("isSubscribed", player.isSubscribed())
                .append("boughtTickets", ticketDocs));
        playersCollection.updateOne(new Document("_id", player.getId()), updated);
    }

    @Override
    public void delete(Player player) {
        playersCollection.deleteOne(new Document("_id", player.getId()));
    }

    private Player documentToPlayer(Document doc) {
        Player player = new Player(
                doc.getObjectId("_id"),
                doc.getString("name"),
                doc.getString("email")
        );
        player.setSubscribed(doc.getBoolean("isSubscribed"));
        player.setTicketsBought(doc.getList("ticketsBought", Ticket.class));
        return player;
    }
}
